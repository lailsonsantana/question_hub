import React, { useState } from 'react';

interface RadioProps{

}

const RadioButton: React.FC<RadioProps> = () => {
    const [selectedOption, setSelectedOption] = useState<string>("mistral");
    return (
        <div className='flex space-x-8'>
            
            <div className="flex items-center">
                <input 
                id="default-radio-2" 
                type="radio" 
                value="chatgpt" 
                name="default-radio" 
                checked={selectedOption === "chatgpt"} 
                onChange={(e) => setSelectedOption(e.target.value)}
                className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:bg-gray-700 dark:border-gray-600"/>
                <label htmlFor="default-radio-2" className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Chat GPT (Pago)</label>
            </div>
 
        </div>
    );
};
  
export default RadioButton;