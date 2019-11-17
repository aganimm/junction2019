import React from 'react';
import connect from '@vkontakte/vk-connect';
import '@vkontakte/vkui/dist/vkui.css';
import {
  Avatar,
  Button,
  Cell,
  Epic,
  FormLayout,
  Group,
  Panel,
  PanelHeader,
  Select,
  Tabbar,
  TabbarItem,
  Textarea,
  View
} from '@vkontakte/vkui';

import Icon28Newsfeed from '@vkontakte/icons/dist/28/newsfeed';
import Icon28Search from '@vkontakte/icons/dist/28/search';
import Icon28Messages from '@vkontakte/icons/dist/28/message';
import Tutorial from './panels/Tutorial';
import Swipeable from 'react-swipy';
import Card from './component/Card';
import UserCache from './service/UserCache';
import UserService from './service/UserService';
import './fonts.css';
import ProductService from './service/ProductService';

const wrapperStyles = { position: 'relative', width: '250px', height: '250px' };

const appStyles = {
  height: '100%',
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  width: '100%',
  minHeight: '100vh',
  fontFamily: 'sans-serif',
  overflow: 'hidden'
};

const buttonStyle = {
  position: 'absolute',
  bottom: '8vh',
  width: '100%'
};

export default class App extends React.Component {
  state = {
    cards: [],
    activeStory: 'tutorial',
    currentScreen: 'tutorial',

    user: {
      lookingFor: '0',
      description: ''
    }
  };

  onLoadProfile = () => {
    UserService._it.getProfile().then(data => {
      console.log(data);
      const { lookingFor, description } = data;
      this.setState({
        user: {
          lookingFor,
          description
        }
      })
    });

    ProductService._it.getProducts().then(data => {
      console.log(data);
      this.setState({
        cards: data
      })
    })
  };

  constructor (props) {
    super(props);

    connect.subscribe(({ detail: { type, data } }) => {
      if (type === 'VKWebAppUpdateConfig') {
        const schemeAttribute = document.createAttribute('scheme');
        schemeAttribute.value = 'client_light';//data.scheme ? data.scheme : 'client_light';
        document.body.attributes.setNamedItem(schemeAttribute);
      }
      if (type === 'VKWebAppAccessTokenReceived') {
        const { access_token: accessToken } = data;
        UserCache._it.setAccessToken(accessToken);
        UserCache._it.refreshMiniAppToken(() => this.onLoadProfile());
      }
    });

    async function fetchData () {
      connect.send(
        'VKWebAppGetAuthToken',
        { 'app_id': 7211486, 'scope': 'friends,status' }
      );
      const user = await connect.sendPromise('VKWebAppGetUserInfo');

      const { id, first_name, last_name, photo_200 } = user;
      UserCache._it.setUserId(id);
      UserCache._it.refreshMiniAppToken();
      UserCache._it._firstName = first_name;
      UserCache._it._lastName = last_name;
      UserCache._it._photo = photo_200;
    }

    fetchData();
    this.onStoryChange = this.onStoryChange.bind(this);
  }

  onStoryChange (e) {
    this.setState({ activeStory: e.currentTarget.dataset.story })
  }

  updateCurrentScreen = () => {
    this.setState({
      currentScreen: 'main'

    }, () => {
      this.setState({
        activeStory: 'feed'
      })
    })
  }

  userProfileUpdate = () => {
    const { user: { description, lookingFor } } = this.state;
    console.log(description, lookingFor);
  }

  remove = () =>
    this.setState(({ cards }) => ({ cards: cards.slice(1, cards.length) }));

  render () {
    const { cards, user: { description, lookingFor } } = this.state;

    const main = <Epic activeStory={ this.state.activeStory } tabbar={
      <Tabbar>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'feed' }
          data-story="feed"

        ><Icon28Newsfeed/></TabbarItem>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'discover' }
          data-story="discover"
        ><Icon28Search/></TabbarItem>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'messages' }
          data-story="messages"
        ><Icon28Messages/></TabbarItem>
      </Tabbar>
    }>
      <View id="feed" activePanel="feed">
        <Panel id="feed">
          <PanelHeader>Profile</PanelHeader>

          <Group>
            <Cell
              photo={ UserCache._it._photo }
              before={ <Avatar
                src={ UserCache._it._photo }
                size={ 60 }/> }
              size="l"
            >
              { UserCache._it._firstName + ' ' + UserCache._it._lastName }
            </Cell>
          </Group>
          <Group>
            <FormLayout>
              <Textarea top="About me" value={ description }
                        onChange={ event => this.setState({
                          user: {
                            description: event.target.value
                          }
                        }) }/>

              <Select
                top="I'm looking for"
                placeholder="Type"
                bottom={ lookingFor ? '' : 'Select the gender' }
                value={ lookingFor }
                onChange={ event => this.setState({
                  user: {
                    lookingFor: event.target.value
                  }
                }) }
                name="purpose"
              >
                <option value="0">-/-</option>
                <option value="1">Woman</option>
                <option value="2">Man</option>
              </Select>
            </FormLayout>

          </Group>

          <div style={ buttonStyle } onClick={ this.userProfileUpdate }>
            <Button size="xl" level="secondary">Update profile</Button>
          </div>
        </Panel>
      </View>
      <View id="discover" activePanel="discover">
        <Panel id="discover">
          <PanelHeader>Feed</PanelHeader>
          <div style={ appStyles }>
            <div style={ appStyles }>
              <div style={ wrapperStyles }>
                { cards.length > 0 && (
                  <>
                    <Swipeable onAfterSwipe={ this.remove }>
                      <Card product={ cards[0]}/>
                    </Swipeable>
                  </>
                ) }
                { cards.length <= 1 &&
                <Card zIndex={ -2 } /> }
              </div>
            </div>
          </div>
        </Panel>
      </View>
      <View id="messages" activePanel="messages">
        <Panel id="messages">
          <PanelHeader>Selected</PanelHeader>
        </Panel>
      </View>
    </Epic>;

    const tutorial = <Epic activeStory={ this.state.activeStory }>
      <View id='tutorial' activePanel="tutorial">
        <Tutorial id='tutorial' title='Alinder'
                  onClick={ () => {this.updateCurrentScreen()} }/>
      </View>
    </Epic>;

    const shotTutorial = this.state.currentScreen === 'tutorial';
    return (
      shotTutorial ? tutorial : main
    )
  }
}

