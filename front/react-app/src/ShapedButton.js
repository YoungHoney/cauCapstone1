import React from 'react';

function ShapedButton({ text1, onClick }) {
  const buttonStyle = {
    width: '60px',
    height: '50px',
    margin: '3px',// 버튼 사이에 간격을 주기 위한 마진
    backgroundColor: 'white',
    color: 'black',
    border: '1px solid gray', // 검은색 테두리
    cursor: 'pointer',
  };

  return (
    <button style={buttonStyle} onClick={onClick}>
     <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'black' }}>{text1}</span>
    </button>
  );
}

export default ShapedButton;
