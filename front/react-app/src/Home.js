import React, { useState ,useEffect} from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import JosangSearch from './JosangSearch';
import { Route,Routes,Router, BrowserRouter } from 'react-router-dom';
import MyBarChart from './abilitychart';
import image1 from './img/image1.png';
import image2 from './img/image2.png';
import image3 from './img/image3.png';
import image4 from './img/image4.png';
import './SlideShow.css';







function SearchContainer() {




  
  const containerStyle = {
  width: '300px',
  height: '50px',   
  border: '1px solid #000', 
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  padding: '10px',
  position: 'absolute',
  top: '58%',
  left: '50%',
  transform: 'translate(-50%, -50%)'
  };
  
  const inputStyle = {
    width: '200px',
    height: '30px',
    border: '1px solid #ccc',
    marginRight: '10px',
    padding: '5px',
    fontWeight: 'bold', // 굵기 조절
    fontSize: '16px',
  };
  
  const buttonStyle = {
    marginLeft: '10px',
  padding: '14px 14px',
  
  background: 'black',
  color: 'white',
  border: 'none',
  cursor: 'pointer',
  
  
  };
  
  const [searchText, setSearchText] = useState(''); // 초기값: 빈 문자열
  const navigate = useNavigate();

  const handleSearchChange = (event) => {
    setSearchText(event.target.value);
  };

  const handleSearchClick = () => {
    
    navigate(`/ancestor/real/${searchText}`);

  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      navigate(`/ancestor/real/${searchText}`);
    }
  };



  const textStyle = {
    fontWeight: 'bold', // 굵기 조절
    fontFamily: 'Arial, sans-serif', // 글씨체 조절
  };

  
  return (
    <div style={containerStyle}>
      
      <input
        type="text"
        placeholder="이순신(李舜臣)"
        value={searchText}
        onChange={handleSearchChange}
        onKeyPress={handleKeyPress}
        style={inputStyle}
      />
      <button onClick={handleSearchClick} style={buttonStyle}>
        검색
      </button>
    </div>
  );
}


function MyComponent() {
  return (
    <div style={containerStyle}>
      <div style={textStyle}>본관별 조상 찾기</div>
      <Link to="/JosangSearch">
        <button style={buttonStyle} >
        바로가기
      </button>
      </Link>
      
    </div>
    
  );
}

const containerStyle = {
  width: '300px', 
  height: '50px', 
  border: '1px solid #000', 
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  padding: '10px',
  position: 'absolute',
  top: '42%',
  left: '50%',
  transform: 'translate(-50%, -50%)'
};

const textStyle = {
  flex: 1,
  textAlign: 'center',
  fontSize: '22px',
};

const buttonStyle = {
  marginLeft: '20px',
  padding: '14px 14px',
  background: 'black',
  color: 'white',
  border: 'none',
  cursor: 'pointer',
};










function Home() {
  return (
    <div className="Home">
     <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
    
     <MyComponent></MyComponent>
     <SearchContainer></SearchContainer>

    
     
     

    </div>
    
  );
}

export default Home;

