import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import Name from './name';
import VirtualExpandableComponent1 from './virtualexpandablecomponent1';
import VirtualExpandableComponent2 from './virtualexpandablecomponent2';
import VirtualExpandableComponent3 from './virtualexpandablecomponent3';






function VirtualResultPage() {

  let { name } = useParams();
        
  console.log(name);
  


  const [jsonconvert, setJsonConvert] = useState(null);
  const [jsondata, setJsonData] = useState(null);

  useEffect(() => {
    if (name) {
      const encodedName = encodeURIComponent(name);
      console.log(encodedName)
      axios.get(`api/ancestor/name/${encodedName}`)
        .then((response) => {
          const jsonconvert = response.data;
          console.log('jsonconvert:', jsonconvert); // jsonconvert 값을 콘솔에 로깅
  
          setJsonConvert(jsonconvert);
  
          // jsonconvert.id 값이 존재할 때 추가적인 API 호출
          if (jsonconvert && jsonconvert.id) {
            axios.get(`api/ancestor/${jsonconvert.id}`)
              .then((response) => {
                const jsonData = response.data;
                console.log('jsonData:', jsonData); // jsonData 값을 콘솔에 로깅
  
                setJsonData(jsonData);
              })
              .catch((error) => {
                console.error('JSON 데이터를 가져오는 동안 오류가 발생했습니다: ', error);
              });
          }
        })
        .catch((error) => {
          console.error('JSONData 데이터를 가져오는 동안 오류가 발생했습니다: ', error);
        });
    }
  }, [name]);




  

   

  const ConvertButton = () => {

  

    const handleButtonClick = () => {
      console.log('없나?:', jsonconvert); 
      if (jsonconvert && jsonconvert.id) {
        console.log('chatidaaaaa:', jsonconvert.id); 
        window.location.href = `http://3.39.127.44:8080/api/ancestor/${jsonconvert.id}/chat`;
        scrollToTop();
      } else {
        console.error('jsonconvert가 유효하지 않습니다. 아직 데이터가 로딩 중일 수 있습니다.');
      }
    };
   
  
    const scrollToTop = () => {
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    };
  
  
  
    return (
      <div>
      
        <button
          style={{
            position: 'fixed',
            bottom: '20px',
            right: '20px',
            padding: '10px',
            fontSize: '16px',
            backgroundColor: '#333',
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
            
          }}
          onClick={ handleButtonClick}
        >
         조상님과 대화하기
        </button>
     
        <Link to={`/ancestor/real/${name}`}>
        <button
          style={{
            position: 'fixed',
            bottom: '20px',
            left: '20px',
            padding: '10px',
            fontSize: '16px',
            backgroundColor: '#333',
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          
          }}
          onClick={scrollToTop}
        >
         실화로 전환
        </button>
        </Link>
      </div>
    );
  };
  
  
  
  
  













    return (
      <div className="VirtureResultPage">
      
      <Name jsondata={jsondata}/>
      <VirtualExpandableComponent1 jsondata={jsondata}/>
      <VirtualExpandableComponent2 jsondata={jsondata}/>
      <VirtualExpandableComponent3 jsondata={jsondata}/>
     

     <ConvertButton/>
     
      </div>
      
    );
  }


  export default VirtualResultPage