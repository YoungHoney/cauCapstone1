import ENTJ from './img/ENTJ.jpeg';
import ESTJ from './img/ESTJ.jpeg';
import ENFJ from './img/ENFJ.jpeg';
import ENTP from './img/ENTP.jpeg';
import ESTP from './img/ESTP.jpeg';
import ESFP from './img/ESFP.jpeg';
import ESFJ from './img/ESFJ.jpeg';
import ISTJ from './img/ISTJ.jpeg';
import ENFP from './img/ENFP.jpeg';
import INFP from './img/INFP.jpeg';
import INFJ from './img/INFJ.jpeg';
import ISTP from './img/ISTP.jpeg';
import ISFJ from './img/ISFJ.jpeg';
import INTJ from './img/INTJ.jpeg';
import INTP from './img/INTP.jpeg';
import ISFP from './img/ISFP.jpeg';





function Mbti({ jsondata }) {
   
    if (!jsondata || !jsondata.mbti || !jsondata.mbtiContent) {
      return <div style={{ textAlign: 'center', fontWeight: 'bold', fontSize: '24px' }}>데이터가 없습니다.</div>;
      }
      

      const mbtiImageMap = {
        'ENTJ': ENTJ,
        'ESTJ': ESTJ,
        'ENFJ': ENFJ,
        'ENTP': ENTP,
        'ESTP': ESTP,
        'ESFP': ESFP,
        'ESFJ': ESFJ,
        'ENFP': ENFP,
        'ISTJ': ISTJ,
        'ISFP': ISFP,
        'INFP': INFP,
        'INFJ': INFJ,
        'ISTP': ISTP,
        'ISFJ': ISFJ,
        'INTJ': INTJ,
        'INTP': INTP
      };
  
      const imagePath = mbtiImageMap[jsondata.mbti];
  
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
  
    const nameStyle1 = {
  
      fontSize: '70px',
      fontWeight: 'bold',
      color: '#333',
      margin: '20px 0',// 텍스트와 컨테이너 사이의 간격을 없애기 위해 margin을 0으로 설정
      padding: '40px',
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
      <div className="Mbti">
       <br></br>
       <br></br>
       <div style={{ textAlign: 'center' }}>
       <img src={imagePath} width='300px' height='300px' alt={jsondata.mbti} />
        </div>
        <br></br>
        <br></br>
        <div style={{ textAlign: 'center' }}>
        {jsondata && jsondata.mbti && (
  <span style={nameStyle1}>{jsondata.mbti}</span>
  )}
  </div>
  <br></br>
       <br></br>
  <div style={containerStyle}>
  {jsondata && jsondata.mbtiContent && (
  <span style={nameStyle}>{jsondata.mbtiContent}</span>
  )}
  </div>
       
        
         
        
       
        
      </div>
        
      );
    }
    
    export default Mbti;
  
  