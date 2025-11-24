'use client'
import 'flowbite';


import { Template } from "@/components/Template"

import Accordion from "@/components/about/Accordion"
import MainTitle from '@/components/MainTitle';


export default function AjudaPage() {

    return(
        <Template>
            <div className='mb-16'>
                <MainTitle titulo='Sobre Nós'/>
            </div>

            <div className='container flex flex-col gap-12 text-justify text-lg'>
                <p>
                    Question Hub é uma plataforma educativa cujo intuito é permitir que estudantes 
                    criem questões sobre assuntos específicos da área de Ciência da Computação (até o momento)
                    e responda questões criadas por outros alunos.
                </p>


                <p>
                    O software surgiu como implementação do trabalho de conclusão de curso do graduando em 
                    Ciência da Computação
                    <a href="https://www.linkedin.com/in/lailson-santana-dev/" className="text-blue-600 hover:underline m-2">Lailson Santana</a>
                    ,com orientação do professor doutor<a href="https://www.linkedin.com/in/gilnovais/" className="text-blue-600 hover:underline m-4">Gidevaldo Novais</a>.
                    Detalhes do trabalho podem ser vistos através desse link <a href="" className="text-blue-600 hover:underline m-2">TCC Question Hub</a>.
                </p>


                <p>
                    Embora o intuito principal da plataforma seja a criação de questões, algumas outras funcionalidades podem ser exploradas na plataforma,
                    de forma resumida você também pode responder questões, fazer comentários nas questões criadas por outros
                    usuários, avaliar a qualidade das questões criadas e modificar questões criadas por outras pessoas (uma espécie de versionamento),
                    mais detalhes sobre funcionalidades e sobre como usar a plataforma podem ser encontrados na página de 
                    <a href="" className="text-blue-600 hover:underline m-2">Ajuda</a>.
                </p>

                <p>
                    Esperamos que os usuários possam extrair o máximo de potencial da plataforma, de forma que potencializem seu conhecimento
                    e colaborem para a produção de conteúdos que podem ser utilizados por outros usuários, assim como, desfrutem de conteúdos
                    presentes aqui.
                </p>
            
            </div>

            <div className='mt-16'>

            </div>

            {/*<Accordion />*/}
        </Template>
    )
}