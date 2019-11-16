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
import '../custom/Header.css';

import Card from '../custom/Card';

const Feed = () => {
	const [activeView, setActiveView] = useState('main');

	return (
		<View activePanel='main'>
			<Panel id='main'>
				<PanelHeader>
					Лента
				</PanelHeader>
				<div className="circle"></div>
                <div className="cards">
				    <Card image='//placehold.it/500x100' name='Xiaomi Mi Band 4' price='300' rate='3' />
                </div>
			</Panel>
		</View>
	);
};

export default Feed;
