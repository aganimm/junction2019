import React, { useState, useEffect } from 'react';

import '../fonts.css';
import './Card.css';

const Card = (props) => {
	return (
		<div className='card'>
            <div className='card_image'>
                <img src={ props.image } alt='img' />
            </div>
            <div className='card_info'>
                info vfnnvnkdfjfdjfjfnfdkfdnfd
            </div>
        </div>
    );
};

export default Card;
