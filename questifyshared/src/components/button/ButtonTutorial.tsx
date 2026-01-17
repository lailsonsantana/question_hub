import React from 'react';

interface ButtonTutorialProps{

}

const ButtonTutorial: React.FC<ButtonTutorialProps> = () => {
    return (
        
        <div className='p-4'>
            <a
                href="https://youtu.be/t-Qew-clWJM" 
                target="_blank" // Abre o link em uma nova aba
                rel="noopener noreferrer" // Boa prática de segurança para links externos
                className="mb-12 inline-flex items-center bg-red-600 text-white py-3 px-6 rounded-lg hover:bg-red-700 transition-all duration-300 shadow-lg hover:shadow-xl"
                >

                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="h-6 w-6 mr-2" 
                    viewBox="0 0 24 24"
                    fill="currentColor"
                >
                    <path d="M19.615 3.184c-3.604-.246-11.631-.245-15.23 0-3.897.266-4.356 2.62-4.385 8.816.029 6.185.484 8.549 4.385 8.816 3.6.245 11.626.246 15.23 0 3.897-.266 4.356-2.62 4.385-8.816-.029-6.185-.484-8.549-4.385-8.816zm-10.615 12.816v-8l8 3.993-8 4.007z" />
                </svg>

                <span className="font-semibold">NOVO POR AQUI ? VEJA ESSE TUTORIAL</span>
            </a>
        </div>
    );
};
  
export default ButtonTutorial;