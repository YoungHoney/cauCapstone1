import './App.css';
import React from 'react';
import { Route,Routes,Link, } from 'react-router-dom';
import Home from './Home';
import JosangSearch from './JosangSearch';
import axios from 'axios';
import RealResultPage from './RealResultPage';
import VirtualResultPage from './VirtualResultPage';


axios.defaults.baseURL = 'http://localhost:8080'; 

const NotFound = () => {
  return (
  <div>
    <div style={{ fontSize: '35px', textAlign: 'center', marginTop: '30px', fontWeight: 'bold'  }}>우리 조상 알기</div>
  <div style={{fontSize: '25px', fontWeight: 'bold', textAlign: 'center',  marginTop: '150px', }}>
    검색 조건에 맞지 않거나 존재하지 않는 조상입니다.</div>;
    </div>
  )
};


function App() {


  return (
    
      <div>
      
        <nav>
          <ul>
            <li>
              <Link to="/" style={{ textDecoration: 'none',color: 'black', fontWeight: 'bold' }}>Home</Link>
              

            </li>
          </ul>
        </nav>

        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route  exact path="/JosangSearch" element={<JosangSearch/>} />
          <Route  exact path="/ancestor/real/:name" element={<RealResultPage/>} />
          <Route  exact path="/ancestor/virtual/:name" element={<VirtualResultPage/>} />
          <Route path="*" element={<NotFound />} />
  </Routes>
      </div>
    
  
    
          
         
      
   
    
    
  );
}

export default App;
