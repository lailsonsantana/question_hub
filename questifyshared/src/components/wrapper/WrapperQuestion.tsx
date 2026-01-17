import React from 'react';

interface WrapperQuestionProps{
    children: React.ReactNode;
}

const WrapperQuestion: React.FC<WrapperQuestionProps> = ({children}) => {
    return (
        <div className='container border border-gray-300 rounded p-4 shadow-md bg-containerColor'>
            {children}
        </div>
    );
};
  
export default WrapperQuestion;