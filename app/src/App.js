import React, { useState, useEffect } from 'react';
import connect from '@vkontakte/vk-connect';
import View from '@vkontakte/vkui/dist/components/View/View';
import ScreenSpinner from '@vkontakte/vkui/dist/components/ScreenSpinner/ScreenSpinner';
import '@vkontakte/vkui/dist/vkui.css';

import Tutorial from './panels/Tutorial';
import './fonts.css';

const App = () => {
	const [activePanel, ] = useState('tutorial');
	const [, setUser] = useState(null);
	const [popout, setPopout] = useState(<ScreenSpinner size='large' />);

	useEffect(() => {
		connect.subscribe(({ detail: { type, data }}) => {
			switch (type) {
				case 'VKWebAppUpdateConfig':
					const schemeAttribute = document.createAttribute('scheme');
					schemeAttribute.value = data.scheme ? data.scheme : 'client_light';
					document.body.attributes.setNamedItem(schemeAttribute);
					break;
				default:
					console.log('new message', type, data);
			}
		});

		connect
			.sendPromise('VKWebAppGetUserInfo')
			.then(data => {
				console.log('success', data);
				setUser(data);
				setPopout(null);
			})
			.catch(error => {
				console.log('error');
			});
	}, []);

	return (
		<View activePanel={activePanel} popout={popout}>
			<Tutorial id='tutorial' title='Alinder' />
		</View>
	);
}

export default App;

