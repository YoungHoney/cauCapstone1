import 권영준 from './img/권영준.png'
import 김동연 from './img/김동연.png'
import 김봉수 from './img/김봉수.png'
import 김선태 from './img/김선태.png'
import 김선호 from './img/김선호.png'
import 김승겸 from './img/김승겸.png'
import 김영주 from './img/김영주.png'
import 김정중 from './img/김정중.png'
import 김진표 from './img/김진표.png'
import 대한민국국군소령 from './img/대한민국 국군 소령.png'
import 대한민국국군소위 from './img/대한민국 국군 소위.png'
import 대한민국국군중령 from './img/대한민국 국군 중령.png'
import 대한민국국군중위 from './img/대한민국 국군 중위.png'
import 대한민국행정부 from './img/대한민국 행정부.png'
import 박강수 from './img/박강수.png'
import 박일하 from './img/박일하.png'
import 서태원 from './img/서태원.png'
import 석용규 from './img/석용규.png'
import 손식 from './img/손식.png'
import 신계용 from './img/신계용.png'
import 신원식 from './img/신원식.png'
import 안철상 from './img/안철상.png'
import 염태영 from './img/염태영.png'
import 오병권 from './img/오병권.png'
import 오세훈 from './img/오세훈.png'
import 유남석 from './img/유남석.png'
import 윤석열 from './img/윤석열.png'
import 윤희근 from './img/윤희근.png'
import 이문수 from './img/이문수.png'
import 이용환 from './img/이용환.png'
import 이원석 from './img/이원석.png'
import 이재명 from './img/이재명.png'
import 이재준 from './img/이재준.png'
import 조종래 from './img/조종래.png'
import 최재해 from './img/최재해.png'
import 추경호 from './img/추경호.png'
import 한덕수 from './img/한덕수.png'
import 한동훈 from './img/한동훈.png'
import 홍두선 from './img/홍두선.png'
import 홍준표 from './img/홍준표.png'





function TodayCharacter({ jsondata }) {
   
    if (!jsondata || !jsondata.modernPersonandGov || jsondata.modernPersonandGov[0]=="대상없음" ) {
        return <div style={{ textAlign: 'center', fontWeight: 'bold', fontSize: '24px' }}>데이터가 없습니다.</div>;
      }
      

      const todaycharacterImageMap = {
        '권영준': 권영준,
        '김동연': 김동연,
        '김봉수': 김봉수,
        '김선태': 김선태,
        '김선호': 김선호,
        '김승겸': 김승겸,
        '김영주': 김영주,
        '김정중': 김정중,
        '김진표': 김진표,
        '대한민국 국군 소령': 대한민국국군소령,
        '대한민국 국군 소위': 대한민국국군소위,
        '대한민국 국군 중령': 대한민국국군중령,
        '대한민국 국군 중위': 대한민국국군중위,
        '대한민국 행정부 계장': 대한민국행정부,
        '대한민국 행정부 주무관': 대한민국행정부,
        '대한민국 행정부 서기': 대한민국행정부,
        '대한민국 행정부 서기보': 대한민국행정부,
        '박강수': 박강수,
        '박일하': 박일하,
        '서태원': 서태원,
        '석용규': 석용규,
        '손식': 손식,
        '신계용': 신계용,
        '신원식': 신원식,
        '안철상': 안철상,
        '염태영': 염태영,
        '오병권': 오병권,
        '오세훈': 오세훈,
        '유남석': 유남석,
        '윤석열': 윤석열,
        '윤희근': 윤희근,
        '이문수': 이문수,
        '이용환': 이용환,
        '이원석': 이원석,
        '이재명': 이재명,
        '이재준': 이재준,
        '조종래': 조종래,
        '최재해': 최재해,
        '추경호': 추경호,
        '한덕수': 한덕수,
        '한동훈': 한동훈,
        '홍두선': 홍두선,
        '홍준표': 홍준표,
      };
  
      const imagePath = todaycharacterImageMap[jsondata.modernPersonandGov[0]];
  
  
  
     
    return (
        <div className="TodayCharacter">
  
        <div style={{ textAlign: 'center' }}>
          <img style={{ }} src={imagePath} width='400px' height='500px'/>
          </div>
          <div style={{ textAlign: 'center',marginTop:'30px', marginBottom:'5px'}}>
            {jsondata && jsondata.modernPersonandGov && (
              <span style={{ fontSize: '40px', fontWeight: 'bold'}}>{jsondata.modernPersonandGov[0]}</span>
            )}
          </div>
          <div style={{ textAlign: 'center',marginTop:'0px', marginBottom:'50px'}}>
            {jsondata && jsondata.modernPersonandGov && (
              <span style={{ fontSize: '20px',}}>({jsondata.modernPersonandGov[1]})</span>
            )}
          </div>
          
           
          
         
          </div>
        
      );
    }
    
    export default TodayCharacter;
  
  