import * as React from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

export default function AccordionUsage() {
  return (
    <div>
      <Accordion>
          <AccordionSummary
            expandIcon={<ExpandMoreIcon />}
            aria-controls="panel1-content"
            id="panel1-header"
          >
            <Typography sx={{ fontWeight: 'bold' }} component="span">O que é o Question Hub ?</Typography>
          </AccordionSummary>
          <AccordionDetails>
            Question Hub é uma plataforma educativa cujo intuito é permitir que estudates 
            crie questões sobre assuntos específicos da área de Ciência da Computação (até o momento)
            e responda questões criadas por outros alunos.
          </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel2-content"
          id="panel2-header"
        >
          <Typography sx={{ fontWeight: 'bold' }} component="span">Quem criou o Question Hub ?</Typography>
        </AccordionSummary>
        <AccordionDetails>
          O software surgiu como implementação do trabalho de conclusão de curso do agora graduado em 
          Ciência da Computação
          <a href="/inicial" className="text-blue-600 hover:underline m-4">Lailson Santana</a>
          ,com orientação do professor doutor<a href="" className="text-blue-600 hover:underline m-4">Gidevaldo Novais</a>.
          Detalhes do trabalho completo pode ser acessado através desse link <a href="" className="text-blue-600 hover:underline m-4">TCC Question Hub</a>.
        </AccordionDetails>
      </Accordion>
      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel3-content"
          id="panel3-header"
        >
          <Typography sx={{ fontWeight: 'bold' }} component="span">Qual objetivo e para que serve o Question Hub ?</Typography>
        </AccordionSummary>
        <AccordionDetails>
        Embora o intuito principal da plataforma seja a criação de questões, algumas outras funcionalidades podem ser exploradas no sistema,
        de forma resumida você também pode responder questões, fazer comentários nas questões criadas por outros
        usuários, avaliar a qualidade das questões criadas e modificar questões criadas por outras pessoas (uma espécie de versionamento),
        mais detalhes sobre funcionalidades e sobre como usar a plataforma podem ser encontrados na página de ajuda.
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls="panel4-content"
          id="panel4-header"
        >
          <Typography sx={{ fontWeight: 'bold' }} component="span">Quais as vantagens de se criar questões ?</Typography>
        </AccordionSummary>
        <AccordionDetails>
        Esse tópico foi melhor abordado e desenvolvido no trabalho teórico, mas de forma resumida o processo
        de criar e analisar questões é um método de aprendizagem ativa que tem como intuito permitir
        que o  aluno construa conhecimento e se engaje mais no seu processo de aprendizado.

        Outro ponto de destalhe é a base de dados que será criada através desse processo, imagine dispor de questões
        nos mais variados estilos e sobre os mais variados temas , podendo analisar as nuances nos pensamentos de outras
        pessoas e responder uma mesma questão com diferentes alterações.
        </AccordionDetails>
      </Accordion>
      
    </div>
  );
}


