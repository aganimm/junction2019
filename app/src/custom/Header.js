import React, { useState, useEffect } from 'react';

import '../fonts.css';
import './Header.css';

const Header = (props) => {
	return (
		<div className='header'>
            { props.title }
        </div>
    );
};

export default Header;
