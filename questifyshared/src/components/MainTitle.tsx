import React from 'react';

interface MainTitleProps {
  titulo: string;
}

const MainTitle: React.FC<MainTitleProps> = ({ titulo }) => {
  return (

    <h1 className="flex justify-center text-2xl sm:text-5xl font-bold text-[#4D4482] drop-shadow-md p-16">
      {titulo}
    </h1>
    
  );
};

export default MainTitle;