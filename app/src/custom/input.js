import React, { useState, useEffect } from 'react';

import '../fonts.css';
import './input.css';

const Input = (props) => {
    //const { title } = this.props;
	return (
        <div className="input">
		 <label>
    {props.title}:
    <input className ="inp"type="text" name="name" />
  </label>
        </div>
    );
};

export default Input;
