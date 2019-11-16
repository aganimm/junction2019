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
    Input,
    FormLayout,
    FormLayoutGroup,
	CellButton
} from '@vkontakte/vkui';

import '../fonts.css';
import './Favorites.css';
import '../custom/Header.css';
import Button from '../custom/button';
//import Input from '../custom/input';

import Icon36Cancel from '@vkontakte/icons/dist/36/cancel';
import Icon28MarketOutline from '@vkontakte/icons/dist/28/market_outline';
import Icon36Like from '@vkontakte/icons/dist/36/like';

const Favorites = () => {
	const [activePanel, setActivePanel] = useState('addList');

	return (
		<View activePanel={ activePanel }>
			<Panel id='main'>
				<PanelHeader>
					Избранное
				</PanelHeader>
				<div className="circle"></div>
                <div className="blocks">
                    <div className="block active">
                        <div className="emj">🎩</div>
                        <div className="title white">Мои</div>
                        <div className="date">3 дня назад</div>
                    </div>
                    <div className="block">
                    <div className="emj">👵</div>
                        <div className="title">Дм маме</div>
                        <div className="date">неделю назад</div>
                    </div>
                    <div className="block">
                        <div className="emj">👵</div>
                        <div className="title">Дм маме</div>
                        <div className="date">неделю назад</div>
                    </div>
                    <div className="block">
                        <div className="emj">👵</div>
                        <div className="title">Дм маме</div>
                        <div className="date">неделю назад</div>
                    </div>
                    <div className="block">
                        <div className="emj">👵</div>
                        <div className="title">Дм маме</div>
                        <div className="date">неделю назад</div>
                    </div>
                    <div className="block">
                        <div className="emj">👵</div>
                        <div className="title">Дм маме</div>
                        <div className="date">неделю назад</div>
                    </div>
                </div>
                  <div>
                      <Button
                      pos="ab" 
                      onClick={() => { setActivePanel('addList') }}
                      title={'Cоздать список'}/>
                  </div>
			</Panel>
			<Panel  id='addList'>
                <PanelHeader>
					<div>Создание списка</div>
				</PanelHeader>
                <FormLayout>
      <FormLayoutGroup top="Название списка">
        <Input type="text" defaultValue="Петров" />
        <Button
                      pos="noab" 
                      onClick={() => { setActivePanel('addList') }}
                      title={'Cоздать'}/>
      </FormLayoutGroup>
    </FormLayout>
			</Panel>
		</View>
	);
};

export default Favorites;
