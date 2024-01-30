import React ,{useState} from "react";
import Timeline from "./Timeline";



const RealExpandableComponent1= ({jsondata}) =>  {


    const buttonStyle = {
        display: 'flex',
        alignItems: 'center',
        padding: '10px',
        backgroundColor: 'white',
        border: '1px solid #000',
        margin: 'auto',
        marginTop: '20px', // 중앙 정렬을 위해 추가
      };
      
      const arrowStyle = (expanded) => ({
        marginLeft: '10px', // 텍스트와 화살표 사이의 간격 조절
        transform: expanded ? "rotate(0deg)" : "rotate(360deg)",
        transition: "transform 0.3s",
      });


    const [isExpanded, setIsExpanded] = useState(false);
  
    const toggleExpand = () => {
      setIsExpanded(!isExpanded);
    };
  
    return (
      <div>
        <button onClick={toggleExpand} style={buttonStyle}>
        <span style={{ fontSize: '24px',}}>타임라인</span>
          <svg
            width="16"
            height="16"
            viewBox="0 0 16 16"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
            style={arrowStyle(isExpanded)}
          >
            <path d={isExpanded ? "M1 8L8 1L15 8" : "M1 8L8 15L15 8"} />
          </svg>
        </button>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        {isExpanded && <Timeline jsondata={jsondata}></Timeline>}
        <br></br>
        <br></br>
        <br></br>
        <br></br>
      </div>
    );
  }
  

  export default RealExpandableComponent1