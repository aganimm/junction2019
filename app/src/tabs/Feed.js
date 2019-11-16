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

import Icon36Cancel from '@vkontakte/icons/dist/36/cancel';
import Icon28MarketOutline from '@vkontakte/icons/dist/28/market_outline';
import Icon36Like from '@vkontakte/icons/dist/36/like';

import Card from '../custom/Card';

const Feed = () => {
	const [activePanel, setActivePanel] = useState('product');

	return (
		<View activePanel={ activePanel }>
			<Panel id='main'>
				<PanelHeader>
					Лента
				</PanelHeader>
				<div className="circle"></div>
                <div className="cards">
				    <Card image='//placehold.it/500x100' name='Xiaomi Mi Band 4' price='300' rate='3'/>
                </div>
				<div className="buttons">
					<div className="skip">
						<Icon36Cancel />
					</div>
					<div className="buy" onClick={() => { setActivePanel('product') }}>
						<Icon28MarketOutline />
					</div>
					<div className="like">
						<Icon36Like />
					</div>
				</div>
			</Panel>
			<Panel id='product'>
				<PanelHeader>
					Xiaomi Mi Band 4
				</PanelHeader>
				<div className="circle"></div>

			</Panel>
		</View>
	);
};

export default Feed;
