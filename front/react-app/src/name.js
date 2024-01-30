import React from 'react';

const Name = ({jsondata}) => {
   
    

    return (
      <div>
      <div style={{ textAlign: 'center',marginTop:'50px', marginBottom:'50px'}}>
      {jsondata && jsondata.ancestor && jsondata.ancestor.name && (
        <span style={{ fontSize: '50px', fontWeight: 'bold'}}>{jsondata.ancestor.name}</span>
      )}
    </div>
    <div style={{ textAlign: 'center',marginTop:'50px', marginBottom:'50px'}}>
    {jsondata && jsondata.definition && (
      <span style={{ fontSize: '30px', fontWeight: 'bold'}}>{jsondata.definition}</span>
    )}
  </div>
  </div>
    
    );
  }

  export default Name
