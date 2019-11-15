import React, { useState, useEffect } from 'react';
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

import '../fonts.css';
import './Feed.css';

import Header from '../custom/Header';
import Card from '../custom/Card';

const Feed = () => {
	const [activeView, setActiveView] = useState('main');

	return (
		<View activePanel='main'>
			<Panel id='main'>
				<Header title='Лента' />
				<Card image='//placehold.it/500x100' name='Xiaomi Mi Band 4' price='300' rate='3' />
			</Panel>
			<Panel id='total'>
				olool
			</Panel>
		</View>
	);
};

export default Feed;
