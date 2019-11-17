import React from 'react';
import OldCard from './cd';


const cardStyles = {
  background: 'whitesmoke',
  borderRadius: 3,
  width: '250px',
  height: '250px',
  cursor: 'pointer',
  userSelect: 'none',
  position: 'absolute',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  top: 0
};

export default class Card extends React.Component {
  render () {
    const { product, zIndex } = this.props;

    console.log(product);

    if (!product) {
      return <div style={ { ...cardStyles, zIndex } }>No more cards</div>
    }

    const {
      image,
      price,
      productId,
      rating,
      title
    } = product;

    return <OldCard />
  }

}
