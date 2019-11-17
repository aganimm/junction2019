import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Panel from '@vkontakte/vkui/dist/components/Panel/Panel';

import Slider from 'react-slick';

import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './Tutorial.css';

import Icon24Back from '@vkontakte/icons/dist/24/back';
import img_1 from '../img/1.png';
import img_2 from '../img/2.png';
import img_3 from '../img/3.png';
import img_4 from '../img/4.png';

const Tutorial = ({ id, title, onClick }) => {
  const [slideNum, setSlideNum] = useState(null);

  return (
    <Panel id={ id }>
      <div className='title'>
        <svg width='148' height='40' viewBox='0 0 148 40' fill='none'
             xmlns='http://www.w3.org/2000/svg'>
          <rect x='27' y='14' width='121' height='16' fill='black'/>
          <rect x='19' y='8' width='78' height='2' fill='black'/>
          <rect x='3' y='8' width='8' height='2' fill='black'/>
          <rect x='11' y='14' width='8' height='2' fill='black'/>
          <rect x='21' y='18' width='8' height='2' fill='black'/>
          <rect x='20' y='28' width='11' height='2' fill='black'/>
          <rect y='22' width='18' height='2' fill='black'/>
        </svg>
        <h1>{ title }</h1>
      </div>
      <div className="slider_block">
        <div className="images slide_2">
          <div className="image img_1">
            <img src={ img_1 } alt="img"/>
          </div>
          <div className="image img_2">
            <img src={ img_2 } alt="img"/>
          </div>
          <div className="image img_3">
            <img src={ img_3 } alt="img"/>
          </div>
          <div className="image img_4">
            <img src={ img_4 } alt="img"/>
          </div>
        </div>
        <Slider { ...{
          dots: true,
          infinite: false,
          arrows: false,
          beforeChange: (prevIndex, nextIndex) => {
            console.log(prevIndex, nextIndex);
            setSlideNum(nextIndex);
          }
        } } className='slider'>
          <div className="slide">
            <div className="slide_title">
              Content
            </div>
            <div className="slide_hr"/>
            <div className="slide_text">
              Bla bla bla
            </div>
          </div>
          <div className="slide">
            <div className="slide_title">
              Как?
            </div>
            <div className="slide_hr"/>
            <div className="slide_text">
              Вы еще не слышали про Alinder?
            </div>
          </div>
          <div className="slide">
            <div className="slide_title">
              Вот так
            </div>
            <div className="slide_hr"/>
            <div className="slide_text">
              Даб даб даб
            </div>
          </div>
        </Slider>
        <div className="buttons">
          <div className={ 'skip ' + (slideNum === 2) } onClick={() => onClick()}>Skip</div>
          <div className={ 'next ' + (slideNum === 2) }
               onClick={ () => {
               	console.log('on click');
               	onClick()} }>
            <Icon24Back/>
          </div>
        </div>
      </div>
    </Panel>
  );
};

Tutorial.propTypes = {
  id: PropTypes.string.isRequired

};

export default Tutorial;
