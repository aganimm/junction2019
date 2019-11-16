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
	const [activePanel, setActivePanel] = useState('main');

	return (
		<View activePanel={ activePanel }>
			<Panel id='main'>
				<PanelHeader>
					Лента
				</PanelHeader>
				<div className="circle"></div>
                <div className="cards">
				    <Card image='//placehold.it/500x100' name='Xiaomi Mi Band 4' price='300' rate='3' onClick={() => { setActivePanel('product') }} />
                </div>
				<div className="buttons">
					<div className="skip">
						<Icon36Cancel />
					</div>
					<div className="buy" >
						<Icon28MarketOutline />
					</div>
					<div className="like">
						<Icon36Like />
					</div>
				</div>
			</Panel>
			<Panel id='product'>
				<div className="bg_img">
					<img src="//placehold.it/500x600" alt="img" />
				</div>
				<div className="panel">
					<div className='card_info'>
						<h1>Xiaomi Mi Beat 2</h1>
						<h2>300 ₽</h2>
						<div className="card_rate">
							<div className="users">
								<div className="user_circles">
									<div className="user_circle">
										<img src="//placehold.it/50x50" alt="img" />
									</div>
									<div className="user_circle">
										<img src="//placehold.it/50x50" alt="img" />
									</div>
									<div className="user_circle">
										<img src="//placehold.it/50x50" alt="img" />
									</div>
									<div className="user_circle">
										<img src="//placehold.it/50x50" alt="img" />
									</div>
								</div>
								<div className="users_more">
									+10
								</div>
							</div>
							<div>
								5 stars
							</div>
						</div>
						<p className="description_title">Описание</p>
						<div className="description">
							ОЛло ло рло рло рло рл ор лор л рл  о р оор орорро ло орлол ор орлоол орро ор ор ор ро роро ор ролро ро ролро орро орролр ро о ол оолоро ол о ол о ллолло  лоорол лоол олол  ороорлл
						</div>
					</div>
				</div>
			</Panel>
		</View>
	);
};

export default Feed;
