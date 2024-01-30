import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';



Modal.setAppElement('#root');



const Timeline = ({jsondata}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedYear, setSelectedYear] = useState('');
      let timelinekeys = [];
      let mainEventskeys = [];


      console.log(jsondata); // 전달된 JSON 데이터를 콘솔에 출력

      // jsondata에서 Timeline 객체 확인
      console.log(jsondata && jsondata.timeline);
      
      // Timeline 객체가 존재할 때 해당 객체의 키 배열 확인
      if (jsondata && jsondata.timeline) {
        timelinekeys = Object.keys(jsondata.timeline);
        console.log(timelinekeys);
      } else {
        console.log('jsondata or jsondata.timeline is null or undefined');
      }
    
      if (jsondata && jsondata.mainEvents) {
        mainEventskeys = Object.keys(jsondata.mainEvents);
        console.log(mainEventskeys);
      } else {
        console.log('jsondata or jsondata.mainEvents is null or undefined');
      }

      const timelinekeysintValue1 = parseInt(timelinekeys[0]);
      const timelinekeysintValue2 = parseInt(timelinekeys[1]);
      const timelinekeysintValue3 = parseInt(timelinekeys[2]);
      const timelinekeysintValue4 = parseInt(timelinekeys[3]);
      const timelinekeysintValue5 = parseInt(timelinekeys[4]);
      const timelinekeysintValue6 = parseInt(timelinekeys[5]);

      const mainEventkeysintValue1 = parseInt(mainEventskeys[0]);
      const mainEventkeysintValue2 = parseInt(mainEventskeys[1]);
      const mainEventkeysintValue3 = parseInt(mainEventskeys[2]);
      const mainEventkeysintValue4 = parseInt(mainEventskeys[3]);
     
      
      console.log(timelinekeysintValue1)
      let birthYear = '';
      let deathYear = '';

      if (jsondata && jsondata.ancestor && jsondata.ancestor.birthyear &&jsondata.ancestor.deathyear&& jsondata.ancestor.birthyear=="미상" && jsondata.ancestor.deathyear=="미상") {
       return (
        <p><div style={{ textAlign: 'center', fontWeight: 'bold', fontSize: '24px' }}>데이터가 없습니다.</div></p>
       )
      }
      
      if (jsondata && jsondata.ancestor && jsondata.ancestor.birthyear &&jsondata.ancestor.deathyear&& jsondata.ancestor.birthyear=="미상") {
        birthYear = parseInt(jsondata.ancestor.deathyear) - 100;
      }
      console.log(birthYear);
      console.log(deathYear);

      if (jsondata && jsondata.ancestor && jsondata.ancestor.birthyear &&  jsondata.ancestor.deathyear && jsondata.ancestor.deathyear=="미상") {
        deathYear = parseInt(jsondata.ancestor.birthyear) + 100;
      }
      console.log(birthYear);
      console.log(deathYear);

      if (jsondata && jsondata.ancestor && jsondata.ancestor.birthyear && jsondata.ancestor.birthyear!="미상") {
        birthYear = parseInt(jsondata.ancestor.birthyear);
      }

      console.log(birthYear)
      console.log(deathYear);

      if (jsondata && jsondata.ancestor && jsondata.ancestor.deathyear && jsondata.ancestor.deathyear!="미상") {
        deathYear = parseInt(jsondata.ancestor.deathyear);
      }

console.log(birthYear);
console.log(deathYear);
console.log(typeof birthYear);
console.log(typeof deathYear);

     
      
      const timelineleftValue1 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue1 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';
  console.log(timelineleftValue1)
  console.log(typeof timelineleftValue1)

  const timelineleftValue2 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue2 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';
  console.log(timelineleftValue2)
  console.log(typeof timelineleftValue2)

  const timelineleftValue3 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue3 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const timelineleftValue4 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue4 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const timelineleftValue5 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue5 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const timelineleftValue6 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((timelinekeysintValue6 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';




  const mainEventsleftValue1 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((mainEventkeysintValue1 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const mainEventsleftValue2 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((mainEventkeysintValue2 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const mainEventsleftValue3 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((mainEventkeysintValue3 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  const mainEventsleftValue4 = jsondata && jsondata.timeline &&
  jsondata.ancestor && jsondata.ancestor.birthyear &&
  jsondata.ancestor.deathyear ?
  ((mainEventkeysintValue4 - birthYear) /
  (deathYear - birthYear)) * 100 :
  '정보없음';

  


      const finaltimelineleftValue1 = Math.floor(timelineleftValue1);
      const finaltimelineleftValue2 = Math.floor(timelineleftValue2);
      const finaltimelineleftValue3 = Math.floor(timelineleftValue3);
      const finaltimelineleftValue4 = Math.floor(timelineleftValue4);
      const finaltimelineleftValue5 = Math.floor(timelineleftValue5);
      const finaltimelineleftValue6 = Math.floor(timelineleftValue6);

      const finalmainEventsleftValue1 = Math.floor(mainEventsleftValue1);
      const finalmainEventsleftValue2 = Math.floor(mainEventsleftValue2);
      const finalmainEventsleftValue3 = Math.floor(mainEventsleftValue3);
      const finalmainEventsleftValue4 = Math.floor(mainEventsleftValue4);






      console.log(timelineleftValue1)
console.log(finaltimelineleftValue1)
console.log(typeof timelineleftValue1);
console.log(timelinekeys[0])




      const timelineStyles = {
        timeline: {
          position: 'relative',
          width: '1200px',
          background: '#000',
          margin: 'auto',
          top: '200px',
        },
        horizontalLine: {
          width: '100%',
          height: '3px',
          background: 'hsl(0, 0%, 0%)',
        },


        birthverticalline: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '20px',
          bottom: '0px',
          left: `0%`,
          },

          deathverticalline: {
            position: 'absolute',
            width: '1px',
            background: '#000',
            height: '20px',
            bottom: '0px',
            right: `0%`,
            },

            bdContainer: {
              textAlign: 'center',
              position: 'absolute',
              marginBottom:'20px',
              
              bottom: '0%',
              left: '50%',
              transform: 'translateX(-50%)',
              border: 'none',
              width: '110px',
              height: 'auto',
              padding: '0px',
              borderRadius: '5px',
              lineHeight: '0.9', 
              paddingBottom: '0px'
            },
        


        mainverticallineone: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '50px',
          bottom: '0px',
          left: `${finalmainEventsleftValue1}%`,
          
          
          
        },

        mainverticallinetwo:{
            position: 'absolute',
            width: '1px',
            background: '#000',
            height: '130px',
            bottom: '0px',
            left: `${finalmainEventsleftValue2}%`,
            },

        mainverticallinethree:{
            position: 'absolute',
            width: '1px',
            background: '#000',
            height: '50px',
            bottom: '0px',
            left: `${finalmainEventsleftValue3}%`,
            },


         mainverticallinefour:{
              position: 'absolute',
              width: '1px',
              background: '#000',
              height: '130px',
              bottom: '0px',
              left: `${finalmainEventsleftValue4}%`,
            },
        
       
        
        
        
        
       

          
       

        mainContainerone: {
          textAlign: 'center',
          position: 'absolute',
          marginBottom:'50px',
          
          bottom: '0%',
          left: '50%',
          transform: 'translateX(-50%)',
          border: 'none',
          width: '110px',
          height: 'auto',
          padding: '0px',
          borderRadius: '5px',
          lineHeight: '0.9', 
          paddingBottom: '0px'
        },

        mainContainertwo: {
          textAlign: 'center',
          position: 'absolute',
          marginBottom:'130px',
          
          bottom: '0%',
          left: '50%',
          transform: 'translateX(-50%)',
          border: 'none',
          width: '110px',
          height: 'auto',
          padding: '0px',
          borderRadius: '5px',
          lineHeight: '0.9', 
          paddingBottom: '0px'
        },

        mainContainerthree: {
          textAlign: 'center',
          position: 'absolute',
          marginBottom:'50px',
          
          bottom: '0%',
          left: '50%',
          transform: 'translateX(-50%)',
          border: 'none',
          width: '110px',
          height: 'auto',
          padding: '0px',
          borderRadius: '5px',
          lineHeight: '0.9', 
          paddingBottom: '0px'
        },

        mainContainerfour: {
          textAlign: 'center',
          position: 'absolute',
          marginBottom:'130px',
          
          bottom: '0%',
          left: '50%',
          transform: 'translateX(-50%)',
          border: 'none',
          width: '110px',
          height: 'auto',
          padding: '0px',
          borderRadius: '5px',
          lineHeight: '0.9', 
          paddingBottom: '0px'
        },

        mainContainerText: {
          
          fontSize: '16px',
          fontWeight: 'bold',
        },

        



        eventVerticalLineone: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue1}%`,
        },

        eventVerticalLinetwo: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue2}%`,
        },

        eventVerticalLinethree: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue3}%`,
        },

        eventVerticalLinefour: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue4}%`,
        },

        eventVerticalLinefive: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue5}%`,
        },

        eventVerticalLinesix: {
          position: 'absolute',
          width: '1px',
          background: '#000',
          height: '100px',
          left: `${finaltimelineleftValue6}%`,
        },

        

        eventContainer: {
          position: 'absolute',
          top: '100px',
          left: '50%',
          transform: 'translateX(-50%)',
          border: '1px solid #000',
          width: 'auto',
          padding: '10px',
          borderRadius: '8px',
        },
        eventContainerText: {
          margin: '0',
          fontSize: '12px',
        },


     



        crossPointStyle1: {
          position: 'absolute',
          width: '10px',
          height: '10px',
          backgroundColor: '#000000', // 점의 색상을 지정하세요
          borderRadius: '50%', // 동그랗게 만듭니다.
          top: '0%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
        },

        crossPointStyle2: {
          position: 'absolute',
          width: '10px',
          height: '10px',
          backgroundColor: '#000000', // 점의 색상을 지정하세요
          borderRadius: '50%', // 동그랗게 만듭니다.
          top: '100%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
        },

      };

     
      
     
          
          
          
          
        

        
        // 이하 생략
    


    
    
    
   

    




    const openModal = (year) => {
      setSelectedYear(year);
      setIsModalOpen(true);
    };
  
    const closeModal = () => {
      setSelectedYear('');
      setIsModalOpen(false);
    };
  
    
  
    return (
      <div>
      <div style={timelineStyles.timeline} >
     
  
        <div style={timelineStyles.horizontalLine}></div>

        <div style ={timelineStyles.birthverticalline}>
        <div style={timelineStyles.crossPointStyle2}></div>
        <div style={timelineStyles.bdContainer}>
        <div style={timelineStyles.mainContainerText}>
        {jsondata && jsondata.ancestor && jsondata.ancestor.birthyear &&
  <p >{birthYear}년</p>}
  
         </div>
         </div>
          
        </div>

        <div style ={timelineStyles.deathverticalline}>
        <div style={timelineStyles.crossPointStyle2}></div>
        <div style={timelineStyles.bdContainer}>
        <div style={timelineStyles.mainContainerText}>
        {jsondata && jsondata.ancestor && jsondata.ancestor.deathyear &&
  <p >{deathYear}년</p>}
         </div>
         </div>
          
        </div>
        


      
        
        {jsondata && jsondata.mainEvents && jsondata.mainEvents[mainEventskeys[0]] && (
  <div style={timelineStyles.mainverticallineone}>
    <div style={timelineStyles.crossPointStyle2}></div>
    <div style={timelineStyles.mainContainerone}>
      <div style={timelineStyles.mainContainerText}>
        {mainEventskeys[0]}년
      </div>
      <p style={{ fontSize: '14px' }}>
        {jsondata.mainEvents[mainEventskeys[0]]}
      </p>
    </div>
  </div>
)}
        {jsondata && jsondata.mainEvents && jsondata.mainEvents[mainEventskeys[1]] && (
  <div style={timelineStyles.mainverticallinetwo}>
    <div style={timelineStyles.crossPointStyle2}></div>
    <div style={timelineStyles.mainContainertwo}>
      <div style={timelineStyles.mainContainerText}>
        {mainEventskeys[1]}년
      </div>
      <p style={{ fontSize: '14px' }}>
        {jsondata.mainEvents[mainEventskeys[1]]}
      </p>
    </div>
  </div>
)}
        {jsondata && jsondata.mainEvents && jsondata.mainEvents[mainEventskeys[2]] && (
  <div style={timelineStyles.mainverticallinethree}>
    <div style={timelineStyles.crossPointStyle2}></div>
    <div style={timelineStyles.mainContainerthree}>
      <div style={timelineStyles.mainContainerText}>
        {mainEventskeys[2]}년
      </div>
      <p style={{ fontSize: '14px' }}>
        {jsondata.mainEvents[mainEventskeys[2]]}
      </p>
    </div>
  </div>
)}
        {jsondata && jsondata.mainEvents && jsondata.mainEvents[mainEventskeys[3]] && (
  <div style={timelineStyles.mainverticallinefour}>
    <div style={timelineStyles.crossPointStyle2}></div>
    <div style={timelineStyles.mainContainerfour}>
      <div style={timelineStyles.mainContainerText}>
        {mainEventskeys[3]}년
      </div>
      <p style={{ fontSize: '14px' }}>
        {jsondata.mainEvents[mainEventskeys[3]]}
      </p>
    </div>
  </div>
)}
        
        {timelinekeys[0] && (
  <div
    style={timelineStyles.eventVerticalLineone}
    onClick={() => openModal([timelinekeys[0]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>첫번째 사건</p>
    </div>
  </div>
)}
  
  {timelinekeys[1] && (
  <div
    style={timelineStyles.eventVerticalLinetwo}
    onClick={() => openModal([timelinekeys[1]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>두번째 사건</p>
    </div>
  </div>
)}
  
  {timelinekeys[2] && (
  <div
    style={timelineStyles.eventVerticalLinethree}
    onClick={() => openModal([timelinekeys[2]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>세번째 사건</p>
    </div>
  </div>
)}
  
  {timelinekeys[3] && (
  <div
    style={timelineStyles.eventVerticalLinefour}
    onClick={() => openModal([timelinekeys[3]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>네번째 사건</p>
    </div>
  </div>
)}
  
  {timelinekeys[4] && (
  <div
    style={timelineStyles.eventVerticalLinefive}
    onClick={() => openModal([timelinekeys[4]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>다섯번째 사건</p>
    </div>
  </div>
)}
  
  {timelinekeys[5] && (
  <div
    style={timelineStyles.eventVerticalLinesix}
    onClick={() => openModal([timelinekeys[5]])}
  >
    <div style={timelineStyles.crossPointStyle1}></div>
    <div style={timelineStyles.eventContainer}>
      <p>여섯번째 사건</p>
    </div>
  </div>
)}
        
        <Modal
          isOpen={isModalOpen}
          onRequestClose={closeModal}
          contentLabel="모달"
          style={{
            overlay: {
              backgroundColor: 'rgba(0, 0, 0, 0.5)',
              zIndex: 1000
            },
            content: {
              width: '400px',
              height: '100px', 
              margin: 'auto',
              padding: '20px',
              backgroundColor: 'white',
              borderRadius: '8px',
              boxShadow: '0 2px 4px rgba(0, 0, 0, 0.2)',
              textAlign: 'center'
            }
          }}
        >
          <div className="modal-content">
          {jsondata && jsondata.timeline && selectedYear && (
        <p>{selectedYear}년 : {jsondata.timeline[selectedYear]}</p>
      )}
            <button onClick={closeModal}>닫기</button>
          </div>
        </Modal>
      
      </div>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>
      <br></br>

     
     
      </div>
      
    );
  };
  
  export default Timeline;
  