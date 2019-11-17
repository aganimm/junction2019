import React, { useState, useEffect } from 'react';

import '../fonts.css';
import './cd.css';

export default class OldCard extends React.Component {
    render () {
        console.log(this.props);

        const {image, title, price} = this.props.product;
        return (
          <div className='card'>
              <div className='card_image'>
                  <img src={ image } alt='img'/>
              </div>
              <div className='card_info'>
                  <h1>{ title }</h1>
                  <h2>{ price / 100. } ₽</h2>
                  <div className="card_rate">
                      <div className="users">
                          <div className="user_circles">
                              <div className="user_circle">
                                  <img src="//placehold.it/50x50" alt="img"/>
                              </div>
                              <div className="user_circle">
                                  <img src="//placehold.it/50x50" alt="img"/>
                              </div>
                              <div className="user_circle">
                                  <img src="//placehold.it/50x50" alt="img"/>
                              </div>
                              <div className="user_circle">
                                  <img src="//placehold.it/50x50" alt="img"/>
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
              </div>
          </div>
        );
    }
};