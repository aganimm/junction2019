import React, { useState, useEffect } from 'react';
import connect from '@vkontakte/vk-connect';
import '@vkontakte/vkui/dist/vkui.css';
import {
	Root,
	Epic,
	Tabbar,
	TabbarItem,
	ScreenSpinner,
	View,
	Panel,
	PanelHeader,
	Group,
	CellButton
} from '@vkontakte/vkui';


import Icon28Newsfeed from '@vkontakte/icons/dist/28/newsfeed';
import Icon28Favorite from '@vkontakte/icons/dist/28/favorite';
import Icon28Profile from '@vkontakte/icons/dist/28/profile';

import Home from './panels/Home';
import Persik from './panels/Persik';

const App = () => {
	const [activeStory, setActiveStory] = useState('feed');
	const [activePanel, setActivePanel] = useState('panel1');
	const [popout, setPopout] = useState(<ScreenSpinner size='large' />);
	const [fetchedUser, setUser] = useState(null);

	useEffect(() => {
		connect.subscribe(({ detail: { type, data }}) => {
			if (type === 'VKWebAppUpdateConfig') {
				const schemeAttribute = document.createAttribute('scheme');
				schemeAttribute.value = data.scheme ? data.scheme : 'client_light';
				document.body.attributes.setNamedItem(schemeAttribute);
			}
		});
		async function fetchData() {
			const user = await connect.sendPromise('VKWebAppGetUserInfo');
			setUser(user);
			setPopout(null);
		}
		fetchData();
	}, []);

	const go = e => {
		setActivePanel(e.currentTarget.dataset.to);
	};

	const onStoryChange = e => {
		setActiveStory(e.currentTarget.dataset.story);
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



			<View activePanel={'panel1'}>
				<Panel id="panel1">
					<PanelHeader>Panel 1</PanelHeader>
					<Group>
						<CellButton>
							Go to panel 2
						</CellButton>
					</Group>
				</Panel>
			</View>
		</Epic>
	);
}

export default App;

