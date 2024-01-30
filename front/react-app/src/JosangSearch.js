import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import RealResultPage from './RealResultPage';





function ShapedButton({ text1, onClick }) {
  const buttonStyle1 = {
    width: '60px',
    height: '50px',
    margin: '3px',// 버튼 사이에 간격을 주기 위한 마진
    backgroundColor: 'white',
    color: 'black',
    border: '1px solid gray', // 검은색 테두리
    cursor: 'pointer',
  };

  return (
    <button style={buttonStyle1} onClick={onClick}>
     <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'black' }}>{text1}</span>
    </button>
  );
}







const containerStyle2 = {
  display: 'flex',
  flexDirection: 'row',// 가로로 나열
  flexWrap: 'wrap', // 줄 바
  position:'absolute',
  top:'35%',
  left:'15%', 
 
};

const buttonStyle2 = {
  width: '150px',
  height: '45px',
  margin: '5px',
  backgroundColor: 'white',
  color: 'black',
  border: '1px solid #000',
  cursor: 'pointer',
};



const names1 = ['- 강릉김씨','- 경주김씨',,'- 광산김씨',,'- 김녕김씨','- 김해김씨','- 덕수김씨','- 삼척김씨','- 상산김씨','- 선산김씨','- 곡부공씨','- 안동김씨',
'- 양근김씨','- 안산김씨','- 연안김씨','- 청주김씨','- 희천김씨',
'- 의성김씨','- 청풍김씨','- 청도김씨','- 전주김씨','- 부안김씨','- 순천김씨','- 나주김씨','- 언양김씨','- 울산김씨','- 영광김씨','- 서흥김씨','- 함창김씨','- 광주김씨','- 도강김씨','- 개성김씨','- 진주김씨','- 금릉김씨','- 일성김씨','- 영산김씨','- 수원김씨','- 월성김씨','- 예안김씨','- 금산김씨','- 고령김씨','- 상주김씨','- 풍산김씨','- 고성김씨','- 원주김씨','- 용궁김씨','- 밀양김씨','- 해풍김씨','- 충주김씨','- 서산김씨','- 공주김씨','- 경산김씨',
'- 능성구씨','- 봉화금씨','- 소주가씨',
  '- 수안계씨','- 신천강씨','- 안동권씨',
  '- 예천권씨','- 제주고씨','- 진주강씨','- 평해구씨','- 해평길씨','- 현풍곽씨',];

      const names2 = ['- 강화노씨','- 개성내씨','- 광주노씨','- 교동뇌씨','- 교하노씨',
      '- 금성나씨','- 나주나씨','- 나주내씨','- 만경노씨','- 밀양노씨','- 의령남씨','- 장연노씨','- 진주낭씨',
      '- 함열남궁씨','- 함평노씨',];

      const names3 = ['- 강음단씨','- 광천동씨','- 남원독고씨','- 대산대씨','- 두릉두씨',
      '- 성주도씨','- 연안단씨','- 진주동방씨',];

          const names4 = ['- 고흥류씨','- 문화류씨',];

      const names5 = ['- 감천문씨','- 강화만씨','- 남평문씨','- 목천·장흥마씨','- 사천목씨',
  '- 상곡마씨','- 서촉·연안명씨','- 신창맹씨','- 여흥민씨','- 정선문씨','- 함평모씨',];

  const names6 = ['- 강릉박씨','- 고령박씨','- 구산박씨','- 군위박씨','- 노성박씨',
      '- 면천박씨','- 무안박씨','- 문의박씨','- 밀양박씨','- 반남박씨','- 사천박씨','- 성산배씨','- 수원백씨',
      '- 영암박씨','- 온양방씨','- 운봉박씨','- 울산박씨','- 원주변씨','- 음성박씨','- 의흥박씨','- 죽산박씨','- 진원박씨','- 초계·밀양변씨','- 춘천박씨','- 충주박씨','- 하음봉씨','- 함양박씨',];

      const names7 = ['- 거창신씨','- 고령신씨','- 달성서씨','- 대구서씨','- 밀양손씨',
      '- 보성선씨','- 부여서씨','- 비안손씨','- 삼척심씨','- 순창설씨','- 안동손씨','- 야성송씨','- 영산·영월신씨',
      '- 은진송씨','- 이천서씨','- 장성서씨','- 진주소씨','- 진청송씨','- 창녕성씨','- 청송심씨','- 충주·홍주석씨','- 태원선우씨','- 평산신씨'];

const names8 = ['- 해평윤씨','- 강릉유씨','- 경주이씨','- 광주이씨','- 기계유씨','- 나주임씨',
      '- 남원양씨','- 덕수이씨','- 동복오씨','- 수안이씨','- 순흥안씨','- 영월엄씨','- 영천이씨','- 원주원씨',
      '- 장흥임씨','- 전의이씨','- 전주이씨','- 제주양씨','- 진보이씨','- 탐진안씨','- 파주염씨','- 파평윤씨','- 평택임씨(전객령계)','- 한산이씨','- 함양오씨','- 해남윤씨','- 해주오씨','- 홍천용씨',];

const names9 = ['- 경주정씨','- 광동진씨','- 구례장씨','- 담양전씨','- 동래정씨',
      '- 봉산·봉주지씨','- 신안주씨','- 안동장씨','- 여양진씨','- 영일정씨','- 옥천전씨','- 인동장씨(장계계)','- 인동장씨(장금용계)',
      '- 정선전씨','- 진주정씨','- 창녕조씨','- 초계정씨','- 충주지씨','- 평양조씨','- 평택전씨','- 풍기정씨','- 하동정씨','- 한양조씨','- 함안조씨'];

      const names10 = ['- 간성최씨','- 강릉최씨','- 개성최씨','- 경주최씨','- 낭주최씨',
      '- 동주최씨','- 밀양최씨','- 부안최씨','- 상주최씨','- 수성최씨','- 양주최씨','- 연안차씨','- 영양천씨',
      '- 용궁최씨','- 전주최씨','- 추계추씨','- 통천최씨','- 평강채씨','- 해주최씨','- 화순최씨'];

      const names11 = [];

       const names12 = ['- 과산피씨','- 신창표씨','- 임구풍씨','- 절강팽씨','- 절강편씨'];

       const names13 = ['- 광산탁씨','- 영순태씨','- 협계태씨'];

        const names14 = ['- 강릉·양근함씨','- 곡산한씨','- 김해허씨','- 남양홍씨(당홍계)','- 남양홍씨(토홍계)',
      '- 덕산황씨','- 부계홍씨','- 신평·보안·나주호씨','- 양천허씨','- 연주현씨','- 영천황보씨','- 우주황씨','- 장수황씨',
      '- 제안황씨','- 진주하씨','- 진주형씨','- 창원황씨','- 청주한씨','- 파릉호씨','- 평해황씨','- 홍주홍씨','- 회덕황씨'];





const containerStyle3 = {
    width: '130px',
    height: 'auto',
    backgroundColor: '#F0F0F0',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-start', // 왼쪽 정렬
    justifyContent: 'flex-start', // 위쪽 정렬
    overflowY: 'auto',
    marginTop: '42px',
};

const buttonStyle3 = {
  width: '150px',
  height: '20px',
  margin: '3px',
  backgroundColor: '#F0F0F0',
  color: 'black',
  border: 'none',
  cursor: 'pointer',
  textAlign: 'left',
};







const YourComponent = ( {surname} ) => {
  const [buttonsData, setButtonsData] = useState([]);
  console.log(surname);
  

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (surname) { // surname이 존재할 때만 API 호출
          const encodedSurname = encodeURIComponent(surname);
          const response = await axios.get(`api/search/clan/${encodedSurname}`);
          const data = response.data._embedded?.hashMapList || [];
          setButtonsData(data);
        } else {
          // surname이 존재하지 않을 때 처리할 내용 추가 가능
        }
      } catch (error) {
        console.error('데이터를 불러오는 동안 오류가 발생했습니다:', error);
      }
    };
    
  

    fetchData(surname);

  }, [surname]);

  return (
    <div style={containerStyle2}>
      {buttonsData.map((item, index) => (
        <Link to={`/ancestor/real/${item.name}`} key={index}>
          <button style={buttonStyle2}>
            {item.name}
          </button>
        </Link>
        
      ))}
      
    </div>
  );
};




function JosangSearch() {

  const clearSelectedButton1 = () => {
    setSelectedButton1(null);
  };

  const [selectedButton, setSelectedButton] = useState(null);
  const [selectedButton1, setSelectedButton1] = useState(null);

  return (
    <div className="JosangSearch">
     <div style={{ fontSize: '35px', textAlign: 'center', margin: '40px', fontWeight: 'bold',  }}>본관별 조상 찾기</div>
      <div style={{ display: 'flex',justifyContent: 'center',alignItems: 'center',margin: '40px'}}>
      <ShapedButton text1="ㄱ" onClick={() => {setSelectedButton('ㄱ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㄴ" onClick={() => {setSelectedButton('ㄴ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㄷ" onClick={() => {setSelectedButton('ㄷ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㄹ" onClick={() => {setSelectedButton('ㄹ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅁ" onClick={() => {setSelectedButton('ㅁ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅂ" onClick={() => {setSelectedButton('ㅂ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅅ" onClick={() => {setSelectedButton('ㅅ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅇ" onClick={() => {setSelectedButton('ㅇ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅈ" onClick={() => {setSelectedButton('ㅈ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅊ" onClick={() => {setSelectedButton('ㅊ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅋ" onClick={() => {setSelectedButton('ㅋ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅌ" onClick={() => {setSelectedButton('ㅍ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅍ" onClick={() => {setSelectedButton('ㅌ'); clearSelectedButton1();
        }} />
      <ShapedButton text1="ㅎ" onClick={() => {setSelectedButton('ㅎ'); clearSelectedButton1();
        }} />
      </div>

      {selectedButton === 'ㄱ' && (
        <div style={containerStyle3}>
          {names1.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)} >
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㄴ' && (
        <div style={containerStyle3}>
          {names2.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㄷ' && (
        <div style={containerStyle3}>
          {names3.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㄹ' && (
        <div style={containerStyle3}>
          {names4.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅁ' && (
        <div style={containerStyle3}>
          {names5.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅂ' && (
        <div style={containerStyle3}>
          {names6.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅅ' && (
        <div style={containerStyle3}>
          {names7.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅇ' && (
        <div style={containerStyle3}>
          {names8.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅈ' && (
        <div style={containerStyle3}>
          {names9.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅊ' && (
        <div style={containerStyle3}>
          {names10.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅋ' && (
        <div style={containerStyle3}>
          {names11.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅍ' && (
        <div style={containerStyle3}>
          {names12.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅌ' && (
        <div style={containerStyle3}>
          {names13.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}
      {selectedButton === 'ㅎ' && (
        <div style={containerStyle3}>
          {names14.map((name, index1) => (
            <button key={index1} style={buttonStyle3}
            onClick={() => setSelectedButton1(name)}>
              {name}
            </button>
          ))}
        </div>
      )}


{/* <Link to={`/ancestor/real/${text2}`} key={index2}>
          <button
            style={buttonStyle2}
            onClick={() => setSelectedButton(text2)}
          >
            {text2}
          </button>
        </Link> */}
     
    



  {/* ㄱ */}

{selectedButton1 === '- 강릉김씨' && (
        <div>
        <YourComponent surname='강릉김씨'/>
      </div>
      )}

{selectedButton1 === '- 경주김씨' && (
        <div>
        <YourComponent surname='경주김씨'/>
      </div>
      )}

{selectedButton1 === '- 광산김씨' && (
        <div>
        <YourComponent surname='광산김씨'/>
      </div>
      )}

{selectedButton1 === '- 김녕김씨' && (
        <div>
        <YourComponent surname='김녕김씨'/>
      </div>
      )}

{selectedButton1 === '- 김해김씨' && (
        <div>
        <YourComponent surname='김해김씨'/>
      </div>
      )}

{selectedButton1 === '- 덕수김씨' && (
        <div>
        <YourComponent surname='덕수김씨'/>
      </div>
      )}

{selectedButton1 === '- 삼척김씨' && (
        <div>
        <YourComponent surname='삼척김씨'/>
      </div>
      )}

{selectedButton1 === '- 성산김씨' && (
        <div>
        <YourComponent surname='성산김씨'/>
      </div>
      )}


{selectedButton1 === '- 상산김씨' && (
        <div>
        <YourComponent surname='상산김씨'/>
      </div>
      )}

{selectedButton1 === '- 선산김씨' && (
        <div>
        <YourComponent surname='선산김씨'/>
      </div>
      )}


{selectedButton1 === '- 안동김씨' && (
        <div>
        <YourComponent surname='안동김씨'/>
      </div>
      )}



{selectedButton1 === '- 양근김씨' && (
        <div>
        <YourComponent surname='양근김씨'/>
      </div>
      )}

{selectedButton1 === '- 연안김씨' && (
        <div>
        <YourComponent surname='연안김씨'/>
      </div>
      )}

{selectedButton1 === '- 안산김씨' && (
        <div>
        <YourComponent surname='안산김씨'/>
      </div>
      )}

{selectedButton1 === '- 청주김씨' && (
        <div>
        <YourComponent surname='청주김씨'/>
      </div>
      )}

{selectedButton1 === '- 희천김씨' && (
        <div>
        <YourComponent surname='희천김씨'/>
      </div>
      )}

{selectedButton1 === '- 의성김씨' && (
        <div>
        <YourComponent surname='의성김씨'/>
      </div>
      )}

{selectedButton1 === '- 청풍김씨' && (
        <div>
        <YourComponent surname='청풍김씨'/>
      </div>
      )}

{selectedButton1 === '- 청도김씨' && (
        <div>
        <YourComponent surname='청도김씨'/>
      </div>
      )}

{selectedButton1 === '- 전주김씨' && (
        <div>
        <YourComponent surname='전주김씨'/>
      </div> 
      )}

{selectedButton1 === '- 부안김씨' && (
        <div>
        <YourComponent surname='부안김씨'/>
      </div> 
      )} 
    
{selectedButton1 === '- 순천김씨' && (
        <div>
        <YourComponent surname='순천김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 나주김씨' && (
        <div>
        <YourComponent surname='나주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 언양김씨' && (
        <div>
        <YourComponent surname='언양김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 울산김씨' && (
        <div>
        <YourComponent surname='울산김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 영광김씨' && (
        <div>
        <YourComponent surname='영광김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 서흥김씨' && (
        <div>
        <YourComponent surname='서흥김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 함창김씨' && (
        <div>
        <YourComponent surname='함창김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 광주김씨' && (
        <div>
        <YourComponent surname='광주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 도강김씨' && (
        <div>
        <YourComponent surname='도강김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 개성김씨' && (
        <div>
        <YourComponent surname='개성김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 진주김씨' && (
        <div>
        <YourComponent surname='진주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 금릉김씨' && (
        <div>
        <YourComponent surname='금릉김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 일성김씨' && (
        <div>
        <YourComponent surname='일성김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 영산김씨' && (
        <div>
        <YourComponent surname='영산김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 수원김씨' && (
        <div>
        <YourComponent surname='수원김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 월성김씨' && (
        <div>
        <YourComponent surname='월성김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 예안김씨' && (
        <div>
        <YourComponent surname='예안김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 금산김씨' && (
        <div>
        <YourComponent surname='금산김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 고령김씨' && (
        <div>
        <YourComponent surname='고령김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 상주김씨' && (
        <div>
        <YourComponent surname='상주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 풍산김씨' && (
        <div>
        <YourComponent surname='풍산김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 고성김씨' && (
        <div>
        <YourComponent surname='고성김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 원주김씨' && (
        <div>
        <YourComponent surname='원주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 용궁김씨' && (
        <div>
        <YourComponent surname='용궁김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 밀양김씨' && (
        <div>
        <YourComponent surname='밀양김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 해풍김씨' && (
        <div>
        <YourComponent surname='해풍김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 충주김씨' && (
        <div>
        <YourComponent surname='충주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 서산김씨' && (
        <div>
        <YourComponent surname='서산김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 공주김씨' && (
        <div>
        <YourComponent surname='공주김씨'/>
      </div> 
      )} 

{selectedButton1 === '- 경산김씨' && (
        <div>
        <YourComponent surname='경산김씨'/>
      </div> 
      )} 





      
      
{selectedButton1 === '- 곡부공씨' && (
        <div>
        <YourComponent surname='곡부공씨'/>
      </div>
      )}



{selectedButton1 === '- 능성구씨' && (
        <div>
        <YourComponent surname='능성구씨'/>
      </div>
      )}



{selectedButton1 === '- 봉화금씨' && (
        <div>
        <YourComponent surname='봉화금씨'/>
      </div>
      )}



{selectedButton1 === '- 소주가씨' && (
        <div>
        <YourComponent surname='소주가씨'/>
      </div>
      )}

{selectedButton1 === '- 수안계씨' && (
        <div>
        <YourComponent surname='수안계씨'/>
      </div>
      )}

{selectedButton1 === '- 신천강씨' && (
        <div>
        <YourComponent surname='신천강씨'/>
      </div>
      )}

{selectedButton1 === '- 안동권씨' && (
        <div>
        <YourComponent surname='안동권씨'/>
      </div>
      )}


{selectedButton1 === '- 예천권씨' && (
        <div>
        <YourComponent surname='예천권씨'/>
      </div>
      )}

{selectedButton1 === '- 제주고씨' && (
        <div>
        <YourComponent surname='제주고씨'/>
      </div>
      )}

{selectedButton1 === '- 진주강씨' && (
        <div>
        <YourComponent surname='진주강씨'/>
      </div>
      )}


{selectedButton1 === '- 평해구씨' && (
        <div>
        <YourComponent surname='평해구씨'/>
      </div>
      )}

{selectedButton1 === '- 해평길씨' && (
        <div>
        <YourComponent surname='해평길씨'/>
      </div>
      )}

{selectedButton1 === '- 현풍곽씨' && (
        <div>
        <YourComponent surname='현풍곽씨'/>
      </div>
      )}




{/* ㄴ */}


{selectedButton1 === '- 강화노씨' && (
        <div>
        <YourComponent surname='강화노씨'/>
      </div>
      )}

{selectedButton1 === '- 개성내씨' && (
        <div>
        <YourComponent surname='개성내씨'/>
      </div>
      )}

{selectedButton1 === '- 광주노씨' && (
        <div>
        <YourComponent surname='광주노씨'/>
      </div>
      )}

{selectedButton1 === '- 교동뇌씨' && (
        <div>
        <YourComponent surname='교동뇌씨'/>
      </div>
      )}

{selectedButton1 === '- 교하노씨' && (
        <div>
        <YourComponent surname='교하노씨'/>
      </div>
      )}

{selectedButton1 === '- 금성나씨' && (
        <div>
        <YourComponent surname='금성나씨'/>
      </div>
      )}

{selectedButton1 === '- 나주나씨' && (
        <div>
        <YourComponent surname='나주나씨'/>
      </div>
      )}

{selectedButton1 === '- 나주내씨' && (
        <div>
        <YourComponent surname='나주내씨'/>
      </div>
      )}

{selectedButton1 === '- 만경노씨' && (
        <div>
        <YourComponent surname='만경노씨'/>
      </div>
      )}

{selectedButton1 === '- 밀양노씨' && (
        <div>
        <YourComponent surname='밀양노씨'/>
      </div>
      )}

{selectedButton1 === '- 의령남씨' && (
        <div>
        <YourComponent surname='의령남씨'/>
      </div>
      )}

{selectedButton1 === '- 장연노씨' && (
        <div>
        <YourComponent surname='장연노씨'/>
      </div>
      )}

{selectedButton1 === '- 진주낭씨' && (
        <div>
        <YourComponent surname='진주낭씨'/>
      </div>
      )}

{selectedButton1 === '- 함열남궁씨' && (
        <div>
        <YourComponent surname='함열남궁씨'/>
      </div>
      )}

{selectedButton1 === '- 함평노씨' && (
        <div>
        <YourComponent surname='함평노씨'/>
      </div>
      )}


{/* ㄷ */}


{selectedButton1 === '- 강음단씨' && (
        <div>
        <YourComponent surname='강음단씨'/>
      </div>
      )}

{selectedButton1 === '- 광천동씨' && (
        <div>
        <YourComponent surname='광천동씨'/>
      </div>
      )}

{selectedButton1 === '- 남원독고씨' && (
        <div>
        <YourComponent surname='남원독고씨'/>
      </div>
      )}

{selectedButton1 === '- 대산대씨' && (
        <div>
        <YourComponent surname='대산대씨'/>
      </div>
      )}

{selectedButton1 === '- 두릉두씨' && (
        <div>
        <YourComponent surname='두릉두씨'/>
      </div>
      )}

{selectedButton1 === '- 성주도씨' && (
        <div>
        <YourComponent surname='성주도씨'/>
      </div>
      )}

{selectedButton1 === '- 연안단씨' && (
        <div>
        <YourComponent surname='연안단씨'/>
      </div>
      )}

{selectedButton1 === '- 진주동방씨' && (
        <div>
        <YourComponent surname='진주동방씨'/>
      </div>
      )}


{/* ㄹ */}



{selectedButton1 === '- 고흥류씨' && (
        <div>
        <YourComponent surname='고흥류씨'/>
      </div>
      )}

{selectedButton1 === '- 문화류씨' && (
        <div>
        <YourComponent surname='문화류씨'/>
      </div>
      )}




{/* ㅁ */}


{selectedButton1 === '- 감천문씨' && (
        <div>
        <YourComponent surname='감천문씨'/>
      </div>
      )}

{selectedButton1 === '- 강화만씨' && (
        <div>
        <YourComponent surname='강화만씨'/>
      </div>
      )}

{selectedButton1 === '- 남평문씨' && (
        <div>
        <YourComponent surname='남평문씨'/>
      </div>
      )}

{selectedButton1 === '- 목천·장흥마씨' && (
        <div>
        <YourComponent surname='목천·장흥마씨'/>
      </div>
      )}

{selectedButton1 === '- 사천목씨' && (
        <div>
        <YourComponent surname='사천목씨'/>
      </div>
      )}

{selectedButton1 === '- 상곡마씨' && (
        <div>
        <YourComponent surname='상곡마씨'/>
      </div>
      )}

{selectedButton1 === '- 서촉·연안명씨' && (
        <div>
        <YourComponent surname='서촉·연안명씨'/>
      </div>
      )}

{selectedButton1 === '- 신창맹씨' && (
        <div>
        <YourComponent surname='신창맹씨'/>
      </div>
      )}

{selectedButton1 === '- 여흥민씨' && (
        <div>
        <YourComponent surname='여흥민씨'/>
      </div>
      )}

{selectedButton1 === '- 정선문씨' && (
        <div>
        <YourComponent surname='정선문씨'/>
      </div>
      )}

{selectedButton1 === '- 함평모씨' && (
        <div>
        <YourComponent surname='함평모씨'/>
      </div>
      )}




{/* ㅂ */}




{selectedButton1 === '- 강릉박씨' && (
        <div>
        <YourComponent surname='강릉박씨'/>
      </div>
      )}

{selectedButton1 === '- 고령박씨' && (
        <div>
        <YourComponent surname='고령박씨'/>
      </div>
      )}

{selectedButton1 === '- 구산박씨' && (
        <div>
        <YourComponent surname='구산박씨'/>
      </div>
      )}

{selectedButton1 === '- 군위박씨' && (
        <div>
        <YourComponent surname='군위박씨'/>
      </div>
      )}

{selectedButton1 === '- 노성박씨' && (
        <div>
        <YourComponent surname='노성박씨'/>
      </div>
      )}

{selectedButton1 === '- 면천박씨' && (
        <div>
        <YourComponent surname='면천박씨'/>
      </div>
      )}

{selectedButton1 === '- 무안박씨' && (
        <div>
        <YourComponent surname='무안박씨'/>
      </div>
      )}

{selectedButton1 === '- 문의박씨' && (
        <div>
        <YourComponent surname='문의박씨'/>
      </div>
      )}

{selectedButton1 === '- 밀양박씨' && (
        <div>
        <YourComponent surname='밀양박씨'/>
      </div>
      )}

{selectedButton1 === '- 반남박씨' && (
        <div>
        <YourComponent surname='반남박씨'/>
      </div>
      )}

{selectedButton1 === '- 사천박씨' && (
        <div>
        <YourComponent surname='사천박씨'/>
      </div>
      )}

{selectedButton1 === '- 성산배씨' && (
        <div>
        <YourComponent surname='성산배씨'/>
      </div>
      )}

{selectedButton1 === '- 수원백씨' && (
        <div>
        <YourComponent surname='수원백씨'/>
      </div>
      )}

{selectedButton1 === '- 영암박씨' && (
        <div>
        <YourComponent surname='영암박씨'/>
      </div>
      )}

{selectedButton1 === '- 온양방씨' && (
        <div>
        <YourComponent surname='온양방씨'/>
      </div>
      )}

{selectedButton1 === '- 운봉박씨' && (
        <div>
        <YourComponent surname='운봉박씨'/>
      </div>
      )}

{selectedButton1 === '- 울산박씨' && (
        <div>
        <YourComponent surname='울산박씨'/>
      </div>
      )}

{selectedButton1 === '- 원주변씨' && (
        <div>
        <YourComponent surname='원주변씨'/>
      </div>
      )}

{selectedButton1 === '- 음성박씨' && (
        <div>
        <YourComponent surname='음성박씨'/>
      </div>
      )}

{selectedButton1 === '- 의흥박씨' && (
        <div>
        <YourComponent surname='의흥박씨'/>
      </div>
      )}

{selectedButton1 === '- 죽산박씨' && (
        <div>
        <YourComponent surname='죽산박씨'/>
      </div>
      )}

{selectedButton1 === '- 진원박씨' && (
        <div>
        <YourComponent surname='진원박씨'/>
      </div>
      )}

{selectedButton1 === '- 초계·밀양변씨' && (
        <div>
        <YourComponent surname='초계·밀양변씨'/>
      </div>
      )}

{selectedButton1 === '- 춘천박씨' && (
        <div>
        <YourComponent surname='춘천박씨'/>
      </div>
      )}
      
{selectedButton1 === '- 충주박씨' && (
        <div>
        <YourComponent surname='충주박씨'/>
      </div>
      )}

{selectedButton1 === '- 하음봉씨' && (
        <div>
        <YourComponent surname='하음봉씨'/>
      </div>
      )}

{selectedButton1 === '- 함양박씨' && (
        <div>
        <YourComponent surname='함양박씨'/>
      </div>
      )}




{/* ㅅ */}


{selectedButton1 === '- 거창신씨' && (
        <div>
        <YourComponent surname='거창신씨'/>
      </div>
      )}

{selectedButton1 === '- 고령신씨' && (
        <div>
        <YourComponent surname='고령신씨'/>
      </div>
      )}

{selectedButton1 === '- 달성서씨' && (
        <div>
        <YourComponent surname='달성서씨'/>
      </div>
      )}

{selectedButton1 === '- 대구서씨' && (
        <div>
        <YourComponent surname='대구서씨'/>
      </div>
      )}

{selectedButton1 === '- 밀양손씨' && (
        <div>
        <YourComponent surname='밀양손씨'/>
      </div>
      )}

{selectedButton1 === '- 보성선씨' && (
        <div>
        <YourComponent surname='보성선씨'/>
      </div>
      )}

{selectedButton1 === '- 부여서씨' && (
        <div>
        <YourComponent surname='부여서씨'/>
      </div>
      )}

{selectedButton1 === '- 비안손씨' && (
        <div>
        <YourComponent surname='비안손씨'/>
      </div>
      )}

{selectedButton1 === '- 삼척심씨' && (
        <div>
        <YourComponent surname='삼척심씨'/>
      </div>
      )}

{selectedButton1 === '- 순창설씨' && (
        <div>
        <YourComponent surname='순창설씨'/>
      </div>
      )}

{selectedButton1 === '- 안동손씨' && (
        <div>
        <YourComponent surname='안동손씨'/>
      </div>
      )}

{selectedButton1 === '- 야성송씨' && (
        <div>
        <YourComponent surname='야성송씨'/>
      </div>
      )}

{selectedButton1 === '- 영산·영월신씨' && (
        <div>
        <YourComponent surname='영산·영월신씨'/>
      </div>
      )}

{selectedButton1 === '- 은진송씨' && (
        <div>
        <YourComponent surname='은진송씨'/>
      </div>
      )}

{selectedButton1 === '- 이천서씨' && (
        <div>
        <YourComponent surname='이천서씨'/>
      </div>
      )}

{selectedButton1 === '- 장성서씨' && (
        <div>
        <YourComponent surname='장성서씨'/>
      </div>
      )}

{selectedButton1 === '- 진주소씨' && (
        <div>
        <YourComponent surname='진주소씨'/>
      </div>
      )}

{selectedButton1 === '- 진천송씨' && (
        <div>
        <YourComponent surname='진천송씨'/>
      </div>
      )}


{selectedButton1 === '- 창녕성씨' && (
        <div>
        <YourComponent surname='창녕성씨'/>
      </div>
      )}


{selectedButton1 === '- 청송심씨' && (
        <div>
        <YourComponent surname='청송심씨'/>
      </div>
      )}


{selectedButton1 === '- 충주·홍주석씨' && (
        <div>
        <YourComponent surname='충주·홍주석씨'/>
      </div>
      )}


{selectedButton1 === '- 태원선우씨' && (
        <div>
        <YourComponent surname='태원선우씨'/>
      </div>
      )}


{selectedButton1 === '- 평산신씨' && (
        <div>
        <YourComponent surname='평산신씨'/>
      </div>
      )}


{/* ㅇ */}


{selectedButton1 === '- 해평윤씨' && (
        <div>
        <YourComponent surname='해평윤씨'/>
      </div>
      )}


{selectedButton1 === '- 강릉유씨' && (
        <div>
        <YourComponent surname='강릉유씨'/>
      </div>
      )}


{selectedButton1 === '- 경주이씨' && (
        <div>
        <YourComponent surname='경주이씨'/>
      </div>
      )}


{selectedButton1 === '- 광주이씨' && (
        <div>
        <YourComponent surname='광주이씨'/>
      </div>
      )}


{selectedButton1 === '- 기계유씨' && (
        <div>
        <YourComponent surname='기계유씨'/>
      </div>
      )}


{selectedButton1 === '- 나주임씨' && (
        <div>
        <YourComponent surname='나주임씨'/>
      </div>
      )}


{selectedButton1 === '- 남원양씨' && (
        <div>
        <YourComponent surname='남원양씨'/>
      </div>
      )}


{selectedButton1 === '- 덕수이씨' && (
        <div>
        <YourComponent surname='덕수이씨'/>
      </div>
      )}


{selectedButton1 === '- 동복오씨' && (
        <div>
        <YourComponent surname='동복오씨'/>
      </div>
      )}


{selectedButton1 === '- 수안이씨' && (
        <div>
        <YourComponent surname='수안이씨'/>
      </div>
      )}


{selectedButton1 === '- 순흥안씨' && (
        <div>
        <YourComponent surname='순흥안씨'/>
      </div>
      )}

{selectedButton1 === '- 영월엄씨' && (
        <div>
        <YourComponent surname='열월엄씨'/>
      </div>
      )}


{selectedButton1 === '- 영천이씨' && (
        <div>
        <YourComponent surname='영천이씨'/>
      </div>
      )}


{selectedButton1 === '- 원주원씨' && (
        <div>
        <YourComponent surname='원주원씨'/>
      </div>
      )}


{selectedButton1 === '- 장흥임씨' && (
        <div>
        <YourComponent surname='장흥임씨'/>
      </div>
      )}

{selectedButton1 === '- 전의이씨' && (
        <div>
        <YourComponent surname='전의이씨'/>
      </div>
      )}

{selectedButton1 === '- 전주이씨' && (
        <div>
        <YourComponent surname='전주이씨'/>
      </div>
      )}

{selectedButton1 === '- 제주양씨' && (
        <div>
        <YourComponent surname='제주양씨'/>
      </div>
      )}

{selectedButton1 === '- 진보이씨' && (
        <div>
        <YourComponent surname='진보이씨'/>
      </div>
      )}

{selectedButton1 === '- 탐진안씨' && (
        <div>
        <YourComponent surname='탐진안씨'/>
      </div>
      )}

{selectedButton1 === '- 파주염씨' && (
        <div>
        <YourComponent surname='파주염씨'/>
      </div>
      )}

{selectedButton1 === '- 파평윤씨' && (
        <div>
        <YourComponent surname='파평윤씨'/>
      </div>
      )}

{selectedButton1 === '- 평택임씨(전객령계)' && (
        <div>
        <YourComponent surname='평택임씨(전객령계)'/>
      </div>
      )}

{selectedButton1 === '- 한산이씨' && (
        <div>
        <YourComponent surname='한산이씨'/>
      </div>
      )}

{selectedButton1 === '- 함양오씨' && (
        <div>
        <YourComponent surname='함양오씨'/>
      </div>
      )}

{selectedButton1 === '- 해남윤씨' && (
        <div>
        <YourComponent surname='해남윤씨'/>
      </div>
      )}

{selectedButton1 === '- 해주오씨' && (
        <div>
        <YourComponent surname='홍천용씨'/>
      </div>
      )}






{/* ㅈ */}


{selectedButton1 === '- 경주정씨' && (
        <div>
        <YourComponent surname='경주정씨'/>
      </div>
      )}

{selectedButton1 === '- 광동진씨' && (
        <div>
        <YourComponent surname='광동진씨'/>
      </div>
      )}

{selectedButton1 === '- 구례장씨' && (
        <div>
        <YourComponent surname='구례장씨'/>
      </div>
      )}

{selectedButton1 === '- 담양전씨' && (
        <div>
        <YourComponent surname='담양전씨'/>
      </div>
      )}

{selectedButton1 === '- 동래정씨' && (
        <div>
        <YourComponent surname='동래정씨'/>
      </div>
      )}

{selectedButton1 === '- 봉산·봉주지씨' && (
        <div>
        <YourComponent surname='봉산·봉주지씨'/>
      </div>
      )}

{selectedButton1 === '- 신안주씨' && (
        <div>
        <YourComponent surname='신안주씨'/>
      </div>
      )}

{selectedButton1 === '- 안동장씨' && (
        <div>
        <YourComponent surname='안동장씨'/>
      </div>
      )}

{selectedButton1 === '- 여양진씨' && (
        <div>
        <YourComponent surname='여양진씨'/>
      </div>
      )}

{selectedButton1 === '- 영일정씨' && (
        <div>
        <YourComponent surname='영일정씨'/>
      </div>
      )}

{selectedButton1 === '- 옥천전씨' && (
        <div>
        <YourComponent surname='옥천전씨'/>
      </div>
      )}

{selectedButton1 === '- 인동장씨(장계계)' && (
        <div>
        <YourComponent surname='인동장씨(장계계)'/>
      </div>
      )}

{selectedButton1 === '- 인동장씨(장금용계)' && (
        <div>
        <YourComponent surname='인동장씨(장금용계)'/>
      </div>
      )}

{selectedButton1 === '- 정선전씨' && (
        <div>
        <YourComponent surname='정선전씨'/>
      </div>
      )}

{selectedButton1 === '- 진주정씨' && (
        <div>
        <YourComponent surname='진주정씨'/>
      </div>
      )}

{selectedButton1 === '- 창녕조씨' && (
        <div>
        <YourComponent surname='창녕조씨'/>
      </div>
      )}

{selectedButton1 === '- 초계정씨' && (
        <div>
        <YourComponent surname='초계정씨'/>
      </div>
      )}

{selectedButton1 === '- 충주지씨' && (
        <div>
        <YourComponent surname='충주지씨'/>
      </div>
      )}

{selectedButton1 === '- 평양조씨' && (
        <div>
        <YourComponent surname='평양조씨'/>
      </div>
      )}

{selectedButton1 === '- 평택전씨' && (
        <div>
        <YourComponent surname='평택전씨'/>
      </div>
      )}

{selectedButton1 === '- 풍기정씨' && (
        <div>
        <YourComponent surname='풍기정씨'/>
      </div>
      )}

{selectedButton1 === '- 하동정씨' && (
        <div>
        <YourComponent surname='하동정씨'/>
      </div>
      )}

{selectedButton1 === '- 한양조씨' && (
        <div>
        <YourComponent surname='한양조씨'/>
      </div>
      )}

{selectedButton1 === '- 함안조씨' && (
        <div>
        <YourComponent surname='함안조씨'/>
      </div>
      )}






{/* ㅊ */}

{selectedButton1 === '- 간성최씨' && (
        <div>
        <YourComponent surname='간성최씨'/>
      </div>
      )}

{selectedButton1 === '- 강릉최씨' && (
        <div>
        <YourComponent surname='강릉최씨'/>
      </div>
      )}

{selectedButton1 === '- 개성최씨' && (
        <div>
        <YourComponent surname='개성최씨'/>
      </div>
      )}

{selectedButton1 === '- 경주최씨' && (
        <div>
        <YourComponent surname='경주최씨'/>
      </div>
      )}

{selectedButton1 === '- 낭주최씨' && (
        <div>
        <YourComponent surname='낭주최씨'/>
      </div>
      )}

{selectedButton1 === '- 동주최씨' && (
        <div>
        <YourComponent surname='동주최씨'/>
      </div>
      )}

{selectedButton1 === '- 밀양최씨' && (
        <div>
        <YourComponent surname='밀양최씨'/>
      </div>
      )}

{selectedButton1 === '- 부안최씨' && (
        <div>
        <YourComponent surname='부안최씨'/>
      </div>
      )}

{selectedButton1 === '- 상주최씨' && (
        <div>
        <YourComponent surname='상주최씨'/>
      </div>
      )}

{selectedButton1 === '- 수성최씨' && (
        <div>
        <YourComponent surname='수성최씨'/>
      </div>
      )}

{selectedButton1 === '- 양주최씨' && (
        <div>
        <YourComponent surname='양주최씨'/>
      </div>
      )}

{selectedButton1 === '- 연안차씨' && (
        <div>
        <YourComponent surname='연안차씨'/>
      </div>
      )}

{selectedButton1 === '- 연안차씨' && (
        <div>
        <YourComponent surname='연안차씨'/>
      </div>
      )}

{selectedButton1 === '- 영양천씨' && (
        <div>
        <YourComponent surname='영양천씨'/>
      </div>
      )}

{selectedButton1 === '- 용궁최씨' && (
        <div>
        <YourComponent surname='용궁최씨'/>
      </div>
      )}

{selectedButton1 === '- 전주최씨' && (
        <div>
        <YourComponent surname='전주최씨'/>
      </div>
      )}

{selectedButton1 === '- 추계추씨' && (
        <div>
        <YourComponent surname='추계추씨'/>
      </div>
      )}

{selectedButton1 === '- 통천최씨' && (
        <div>
        <YourComponent surname='통천최씨'/>
      </div>
      )}


{selectedButton1 === '- 평강채씨' && (
        <div>
        <YourComponent surname='평강채씨'/>
      </div>
      )}


{selectedButton1 === '- 해주최씨' && (
        <div>
        <YourComponent surname='해주최씨'/>
      </div>
      )}


{selectedButton1 === '- 화순최씨' && (
        <div>
        <YourComponent surname='화순최씨'/>
      </div>
      )}





{/* ㅍ */}

{selectedButton1 === '- 괴산피씨' && (
        <div>
        <YourComponent surname='괴산피씨'/>
      </div>
      )}

{selectedButton1 === '- 신창표씨' && (
        <div>
        <YourComponent surname='신창표씨'/>
      </div>
      )}

{selectedButton1 === '- 임구풍씨' && (
        <div>
        <YourComponent surname='임구풍씨'/>
      </div>
      )}

{selectedButton1 === '- 절강팽씨' && (
        <div>
        <YourComponent surname='절강팽씨'/>
      </div>
      )}

{selectedButton1 === '- 절강편씨' && (
        <div>
        <YourComponent surname='절간평씨'/>
      </div>
      )}





{/* ㅌ */}


{selectedButton1 === '- 광산탁씨' && (
        <div>
        <YourComponent surname='광산탁씨'/>
      </div>
      )}


{selectedButton1 === '- 영순태씨' && (
        <div>
        <YourComponent surname='영순태씨'/>
      </div>
      )}


{selectedButton1 === '- 협계태씨' && (
        <div>
        <YourComponent surname='협계태씨'/>
      </div>
      )}






{/* ㅎ */}


{selectedButton1 === '- 강릉·양근함씨' && (
        <div>
        <YourComponent surname='강릉·양근함씨'/>
      </div>
      )}

{selectedButton1 === '- 곡산한씨' && (
        <div>
        <YourComponent surname='곡산한씨'/>
      </div>
      )}

{selectedButton1 === '- 김해허씨' && (
        <div>
        <YourComponent surname='김해허씨'/>
      </div>
      )}

{selectedButton1 === '- 남양홍씨(당홍계)' && (
        <div>
        <YourComponent surname='남양홍씨(당홍계)'/>
      </div>
      )}

{selectedButton1 === '- 남양홍씨(토홍계)' && (
        <div>
        <YourComponent surname='남양홍씨(토홍계)'/>
      </div>
      )}

{selectedButton1 === '- 덕산황씨' && (
        <div>
        <YourComponent surname='덕산황씨'/>
      </div>
      )}

{selectedButton1 === '- 부계홍씨' && (
        <div>
        <YourComponent surname='부계홍씨'/>
      </div>
      )}

{selectedButton1 === '- 신평·보안·나주호씨' && (
        <div>
        <YourComponent surname='신평·보안·나주호씨'/>
      </div>
      )}

{selectedButton1 === '- 양천허씨' && (
        <div>
        <YourComponent surname='양천허씨'/>
      </div>
      )}

{selectedButton1 === '- 연주현씨' && (
        <div>
        <YourComponent surname='연주현씨'/>
      </div>
      )}

{selectedButton1 === '- 영천황보씨' && (
        <div>
        <YourComponent surname='영천황보씨'/>
      </div>
      )}

{selectedButton1 === '- 우주황씨' && (
        <div>
        <YourComponent surname='우주황씨'/>
      </div>
      )}

{selectedButton1 === '- 장수황씨' && (
        <div>
        <YourComponent surname='장수황씨'/>
      </div>
      )}

{selectedButton1 === '- 제안황씨' && (
        <div>
        <YourComponent surname='제안황씨'/>
      </div>
      )}

{selectedButton1 === '- 진주하씨' && (
        <div>
        <YourComponent surname='진주하씨'/>
      </div>
      )}

{selectedButton1 === '- 진주형씨' && (
        <div>
        <YourComponent surname='진주형씨'/>
      </div>
      )}

{selectedButton1 === '- 창원황씨' && (
        <div>
        <YourComponent surname='창원황씨'/>
      </div>
      )}

{selectedButton1 === '- 청주한씨' && (
        <div>
        <YourComponent surname='청주한씨'/>
      </div>
      )}

{selectedButton1 === '- 파릉호씨' && (
        <div>
        <YourComponent surname='파릉호씨'/>
      </div>
      )}

{selectedButton1 === '- 평해황씨' && (
        <div>
        <YourComponent surname='평해황씨'/>
      </div>
      )}

{selectedButton1 === '- 홍주홍씨' && (
        <div>
        <YourComponent surname='홍주홍씨'/>
      </div>
      )}

{selectedButton1 === '- 회덕황씨' && (
        <div>
        <YourComponent surname='회덕황씨'/>
      </div>
      )}








 
  
    </div>
    
    
  );
}

export default JosangSearch;

