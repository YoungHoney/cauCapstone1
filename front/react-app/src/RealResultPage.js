import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import Name from './name';
import Josangimage from './josangimage';
import SimpleIntroduction from './simpleintroduction';
import RealExpandableComponent1 from './realexpandablecomponent1';
import RealExpandableComponent2 from './realexpandablecomponent2';
import RealExpandableComponent3 from './realexpandablecomponent3';






      function RealResultPage() {
        let { name } = useParams();
        
        
        console.log(name);



        const isValidNameFormat = (name) => {
          // 이곳에 유효성을 검사하는 정규식 패턴을 작성합니다.
          // 예시: '인물의이름(인물의한자이름)' 형식을 유효한 형식으로 판단합니다.
          const namePattern = /[ㄱ-힣]+\([ㄱ-힣]+\)/; // 예시 패턴입니다.
      
          return namePattern.test(name);
        };

     
        


        const [jsonconvert, setJsonConvert] = useState(null);
        const [jsondata, setJsonData] = useState(null);
      
        useEffect(() => {
          if (name) {
            const encodedName = encodeURIComponent(name);
            console.log(encodedName)
            axios.post(`api/search/${encodedName}`)
              .then((response) => {
                const jsonconvert = response.data;
                console.log('jsonconvert:', jsonconvert); // jsonconvert 값을 콘솔에 로깅
                const chatid = jsonconvert.id;
                console.log('chatid:', chatid); 
                setJsonConvert(jsonconvert);
        
                // jsonconvert.id 값이 존재할 때 추가적인 API 호출
                if (jsonconvert && jsonconvert.id ) {
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
        

console.log(jsondata)







 

     

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
             
                <Link to={`/ancestor/virtual/${name}`}>
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
                 가상으로 전환
                </button>
                </Link>
              </div>
            );
          };
          
          
          
        
          
          if (!isValidNameFormat(name) ) {
            
            if(!jsondata){
            return (
              <div>
                  <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
                <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',  marginTop: '150px', }}>
                  검색 조건에 맞지 않거나 존재하지 않는 조상입니다.</div>;
                  </div>
            );
            }
          }

          


          if (!isValidNameFormat(name) ) {
            return (
              <div>
                  <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
                <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',  marginTop: '150px', }}>
                  검색 조건에 맞지 않습니다.</div>;
                  </div>
            );
          }

          if (isValidNameFormat(name) ) {
            if(!jsondata){
            return (
              <div>
                  <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
                <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',  marginTop: '150px', }}>
                  데이터 로딩중.....</div>;
                  <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',   }}>
                  조상의 정보가 없다면 화면이 바뀌지 않습니다.</div>;
                  </div>
            );
            }
          }


          
          if(jsonconvert.id == -1){
            return (
              <div>
                  <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
                <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',  marginTop: '150px', }}>
                  조상에 대한 데이터가 없습니다</div>;
                  
                  </div>
            );
            }
          

          
   


        return (
            <div className="RealResultPage">
        <Name jsondata={jsondata}/>

        <div style={{display: 'flex', justifyContent: 'center',}}>
       <Josangimage jsondata={jsondata}></Josangimage>
    <SimpleIntroduction jsondata={jsondata}></SimpleIntroduction>
       </div>
       <br></br>
       <br></br>
       <br></br>
       <br></br>

       <RealExpandableComponent1 jsondata={jsondata}/>
       <RealExpandableComponent2 jsondata={jsondata}/>
       <RealExpandableComponent3 jsondata={jsondata}/>

        <ConvertButton></ConvertButton>

         </div>  
        )
    
      }

    
    


      export default RealResultPage

    


