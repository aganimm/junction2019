import React, { useState, useEffect } from 'react';
import connect from '@vkontakte/vk-connect';
import '@vkontakte/vkui/dist/vkui.css';
import {
	Epic,
	Tabbar,
	TabbarItem,
	ScreenSpinner,
	View,
	Panel,
	PanelHeader,
} from '@vkontakte/vkui';


import Icon28Newsfeed from '@vkontakte/icons/dist/28/newsfeed';
import Icon28Favorite from '@vkontakte/icons/dist/28/favorite';
import Icon28Profile from '@vkontakte/icons/dist/28/profile';

import Feed from './tabs/Feed';
import Favorites from './tabs/Favorites';

const App = () => {
	const [activeStory, setActiveStory] = useState('favorites');
	const [fetchedUser, setUser] = useState(null);
	const [popout, setPopout] = useState(<ScreenSpinner size='large' />);

	useEffect(() => {
		connect.subscribe(({ detail: { type, data }}) => {
			if (type === 'VKWebAppUpdateConfig') {
				const schemeAttribute = document.createAttribute('scheme');
				schemeAttribute.value = 'client_light';//data.scheme ? data.scheme : 'client_light';
				document.body.attributes.setNamedItem(schemeAttribute);
			}
		});
		async function fetchData() {
			connect.send("VKWebAppGetAuthToken", {"app_id": 7210035, "scope": "friends,status"});
			const user = await connect.sendPromise('VKWebAppGetUserInfo');
			setUser(user);
			setPopout(null);
		}
		fetchData();
	}, []);

	const onStoryChange = e => {
		setActiveStory(e.currentTarget.dataset.story);
		connect.send('VKWebAppTapticSelectionChanged');
	};

	return (
		<Epic activeStory={ activeStory } tabbar={
			<Tabbar>
				<TabbarItem
					onClick={ onStoryChange }
					selected={ activeStory === 'feed' }
					data-story='feed'
					text='Лента'
				>
					<Icon28Newsfeed />
				</TabbarItem>
				<TabbarItem
					onClick={ onStoryChange }
					selected={ activeStory === 'favorites' }
					data-story='favorites'
					text='Избранное'
				>
					<Icon28Favorite />
				</TabbarItem>
				<TabbarItem
					onClick={ onStoryChange }
					selected={ activeStory === 'profile' }
					data-story='profile'
					text='Профиль'
				>
					<Icon28Profile />
				</TabbarItem>
			</Tabbar>
		}>
			<Feed id='feed' />
			<Favorites id='favorites' />
		</Epic>
	);
};

export default App;
