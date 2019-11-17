import React, { useEffect, useState } from 'react';
import connect from '@vkontakte/vk-connect';
import '@vkontakte/vkui/dist/vkui.css';
import {
  Epic,
  Panel,
  PanelHeader,
  ScreenSpinner,
  Tabbar,
  TabbarItem,
  View
} from '@vkontakte/vkui';

import Icon28Newsfeed from '@vkontakte/icons/dist/28/newsfeed';
import Icon28Search from '@vkontakte/icons/dist/28/search';
import Icon28Messages from '@vkontakte/icons/dist/28/message';
import Icon28Notifications from '@vkontakte/icons/dist/28/notification';
import Icon28More from '@vkontakte/icons/dist/28/more';
import Tutorial from './panels/Tutorial';
import Swipeable from 'react-swipy';
import Card from './component/Card';

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

export default class App extends React.Component {
  constructor (props) {
    super(props);

    this.state = {
      cards: ['First', 'Second', 'Third'],
      activeStory: 'tutorial',
      currentScreen: 'tutorial'
    };
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

  remove = () =>
    this.setState(({ cards }) => ({ cards: cards.slice(1, cards.length) }));

  render () {
    const { cards } = this.state;

    const main = <Epic activeStory={ this.state.activeStory } tabbar={
      <Tabbar>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'feed' }
          data-story="feed"
          text="Новости"

        ><Icon28Newsfeed/></TabbarItem>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'discover' }
          data-story="discover"
          text="Поиск"
        ><Icon28Search/></TabbarItem>
        <TabbarItem
          onClick={ this.onStoryChange }
          selected={ this.state.activeStory === 'messages' }
          data-story="messages"
          text="Сообщения"
        ><Icon28Messages/></TabbarItem>
      </Tabbar>
    }>
      <View id="feed" activePanel="feed">
        <Panel id="feed">
          <PanelHeader>Профиль</PanelHeader>
        </Panel>
      </View>
      <View id="discover" activePanel="discover">
        <Panel id="discover">
          <PanelHeader>Пары</PanelHeader>
          <div className={ appStyles }>
            <div className={ appStyles }>
              { cards.length > 0 && (
                <div style={ wrapperStyles }>
                  <Swipeable onAfterSwipe={ this.remove }>
                    <Card>{ cards[0] }</Card>
                  </Swipeable>
                  { cards.length > 1 &&
                  <Card zIndex={ -1 }>{ cards[1] }</Card> }
                </div>
              ) }
              { cards.length <= 1 && <Card zIndex={ -2 }>No more cards</Card> }
            </div>
          </div>
        </Panel>
      </View>
      <View id="messages" activePanel="messages">
        <Panel id="messages">
          <PanelHeader>Избранное</PanelHeader>
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

