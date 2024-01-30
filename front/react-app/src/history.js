import React from "react";






const History = ({jsondata} ) => {
   
    

    const containerStyle = {
      width: '1000px',
      height: 'auto',
      backgroundColor: '#f5f5f5',
      display: 'flex',
      margin: 'auto',
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: '10px', 
    };
  
    const nameStyle = {

      fontSize: '18px',
      fontWeight: 'bold',
      color: '#333',
      margin: '0', // 텍스트와 컨테이너 사이의 간격을 없애기 위해 margin을 0으로 설정
      padding: '20px',
      lineHeight: '1.5',
      whiteSpace: 'pre-wrap',
    };
  
    return (
      <div style={containerStyle}>
        {jsondata && jsondata.lifeSummary && (
 <span style={nameStyle}>{jsondata.lifeSummary}</span>
)}
       
      </div>
    );
  }

  export default History


