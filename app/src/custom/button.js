import React, { useState, useEffect } from 'react';

import '../fonts.css';
import './button.css';

const Button = (props) => {
    //const { title } = this.props;
	return (
		<div className='btn'>
         <button className={props.pos}>{props.title}</button>
         
        </div>
    );
};

export default Button;
