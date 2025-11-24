interface InputAlternativaProps {
  register?: any; // Defina o tipo de register aqui, pode ser 'UseFormRegisterReturn' do react-hook-form
  name: string;
  isSelected: boolean;
  onSelect: () => void;
  justification: string;
  setJustification: (value: string) => void;
  errorJustificativa?: string
}

const InputAlternativa: React.FC<InputAlternativaProps> = ({
   register, name, isSelected, onSelect, justification, setJustification, errorJustificativa
}) => {
  
  return (
    <div className="w-full flex flex-col items-start border border-gray-300 rounded-md p-4 bg-white">
      <div className="flex flex-grow items-center w-full">
        <input
          required
          className="w-full outline-none focus:placeholder-transparent"
          type='text'
          placeholder='Alternativa'
          {...register(name)}
          autoComplete='off'
        />

        <div className="flex flex-grow items-center">
          <label htmlFor="default-checkbox" className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300 mr-2">Correta</label>
          <input
            id="default-checkbox"
            type="radio"
            checked={isSelected}
            onChange={onSelect}
            className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
          />
        </div>
      </div>

      {isSelected && (
        <textarea
          value={justification}
          onChange={(e) => setJustification(e.target.value)}
          placeholder="Justifique porque essa é a alternativa correta"
          className="w-full mt-2 p-2 border border-gray-300 rounded-md outline-none"
        />
      )}

    </div>
  );
};

export default InputAlternativa;

