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
                <h1>Название</h1>
                <h2>Цена</h2>
                <div className="card_rate">
                    <div>
                        10 users
                    </div>
                    <div>
                        5 stars
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Card;
