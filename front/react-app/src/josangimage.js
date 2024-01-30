import React from "react"
import defaultimage from './img/defaultimage.png';

function Josangimage({ jsondata }) {
    return (
      <div>
        {/* 다른 정보들 */}
        {(jsondata && jsondata.ancestor && jsondata.ancestor.personpicture&&jsondata.ancestor.personpicture!="shes'gone") ? (
          <img
            style={{}}
            src={jsondata.ancestor.personpicture}
            width='300px'
            height='430px'
            
          />
        ) : (
          <img
          style={{}}
          src={defaultimage}
          width='300px'
          height='430px'
         
        />
        )}
   
      </div>
    );
  }
export default Josangimage