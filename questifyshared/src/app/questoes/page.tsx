'use client'

import { Template } from "@/components/Template"
import { useQuestionService } from '@/resources/question/question.service'
import { useEffect, useState } from 'react'
import { Question } from "@/resources/question/question.resource"
import QuestionComponent from "@/components/questao/display/QuestionComponent"
import MultipleSelectCheckmarks from "@/components/questao/create/SeletorDisciplina"
import React from "react"
import { AuthenticatedPage } from "@/components/AuthenticatedPage"
import Button from "@/components/button/ButtonQ"
import Scoreboard from "@/components/questao/display/ScoreBoard"
import { Skeleton } from "@mui/material"
import MainTitle from "@/components/MainTitle"

export default function QuestoesPage() {
    const useServiceQuestion = useQuestionService();
    const [questions, setQuestions] = useState<Question[]>([]);
    const [hasMounted, setHasMounted] = useState(false);
    const [disciplineName, setDisciplineName] = useState<string[]>([]);
    const [isLoading, setIsLoading] = useState(true); // Estado para carregamento inicial
    const [isProcessing, setIsProcessing] = useState(false); // Estado para ações demoradas
    const [acertos , setAcertos] = useState(0);
    const [erros , setErros] = useState(0)


    useEffect(() => {
        setHasMounted(true);
        searchQuestions();
    }, []);

    useEffect(() => {
        const acertosStr = localStorage.getItem('placarA');
        const errosStr = localStorage.getItem('placarE');
        const acertos = acertosStr ? JSON.parse(acertosStr).count : 0;
        const erros = errosStr ? JSON.parse(errosStr).count : 0;
        setAcertos(Number(acertos))
        setErros(Number(erros))
    }, []);

    
    if (!hasMounted) {
        return null;
    }

    async function searchQuestions() {
        setIsLoading(true); // Inicia o carregamento
        try {
            const result = await useServiceQuestion.getAllQuestions();
            setQuestions(result);
        } catch (error) {
            console.error('Erro ao carregar questões:', error);
        } finally {
            setIsLoading(false); // Finaliza o carregamento
        }
    }

    const handleDisciplinesChange = (selectedDisciplines: string[]): void => {
        setDisciplineName(selectedDisciplines);
    };

    async function subjectFilter() {
        setIsProcessing(true); // Inicia o processamento
        try {
            const result = await useServiceQuestion.getQuestionsByDisciplines(disciplineName);
            setQuestions(result);
            setTimeout(() => {}, 500);
        } catch (error) {
            console.error('Erro ao filtrar questões:', error);
        } finally {
            setIsProcessing(false); // Finaliza o processamento
        }
    }

    function mapperQuestion(question: Question) {
        return (
            <div key={question.id}>
                <QuestionComponent
                    id={question.id!}
                    statement={question.statement}
                    answers={question.answers}
                    discipline={question.discipline}
                    userId={question.userId!}
                    nameUser={question.nameUser}
                    previousId={question.previousId}
                    justification={question.justification}
                    createdAt={question.createdAt}
                    countRating={question.countRating}
                    totalRating={question.totalRating}
                />
            </div>
        );
    }

    function mapperQuestions() {
        if (isLoading) {
            // Exibe Skeletons enquanto as questões estão sendo carregadas
            return Array.from({ length: 5 }).map((_, index) => (
                <div key={index} className="p-4 border rounded-lg shadow-md bg-white mb-4">
                  {/* Estrutura da questão */}
                  <Skeleton variant="rectangular" height={60} />
              
                  <Skeleton variant="rectangular" height={400} className="mt-2" />
              
                  <Skeleton variant="rectangular" height={180} className="mt-4" />
                </div>
              ));
        } else {
            return questions.map(mapperQuestion);
        }
    }

    return (
        <AuthenticatedPage>
            <Template loading={isProcessing}>

                <div className="space-y-4">

                    <MainTitle titulo='Lista de Questões'/>

                    <div className="flex items-start mx-6 justify-between">

                        <div className="flex items-center">
                            <MultipleSelectCheckmarks onDisciplinesChange={handleDisciplinesChange} />
                            
                            <Button label="Filtrar" onClick={subjectFilter} />
                        </div>

                        <Scoreboard correct={acertos} incorrect={erros} />
                    </div>

                    <section className="grid grid-cols-1 gap-8">
                        {mapperQuestions()}
                    </section>

                </div>

            </Template>
        </AuthenticatedPage>
    );
}