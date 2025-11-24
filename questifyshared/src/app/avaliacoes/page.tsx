'use client'

import { useState, useEffect } from 'react';
import { Construction, Hammer, Clock, Mail } from 'lucide-react';
import { Template } from '@/components/Template';
import { AuthenticatedPage } from '@/components/AuthenticatedPage';

export default function AvaliaçõesPage() {


  return (
    <AuthenticatedPage>
        <Template>
        <div className="min-h-screen bg-gradient-to-br flex items-center justify-center px-4">
            {/* Background decorativo */}
        
            <div className=" z-10 max-w-4xl w-full">
            <div className="text-center">
                {/* Ícone animado */}
                <div className="flex justify-center mb-8">
                <div className="relative">
                    <Construction className="w-24 h-24 text-[#362975] animate-bounce" />
                    <Hammer className="w-12 h-12 text-[#362975] absolute -bottom-2 -right-2 animate-spin-slow" />
                </div>
                </div>

                {/* Título principal */}
                <h1 className="text-5xl md:text-7xl font-bold text-[#362975] mb-6">
                    Em construção
                </h1>

                {/* Subtítulo */}
                <p className="text-xl md:text-2xl text-[#362975] mb-8 max-w-2xl mx-auto leading-relaxed">
                Estamos trabalhando no desenvolvimento dessa funcionalidade para realização de simulados!
                
                </p>

                {/* Detalhes adicionais */}
                <div className="flex flex-wrap justify-center gap-6 mb-12 text-gray-400">
                <div className="flex items-center gap-3">
                    <Clock className="w-5 h-5 text-purple-400" />
                    <span>Em breve</span>
                </div>
                <div className="flex items-center gap-3">
                    <Construction className="w-5 h-5 text-yellow-400" />
                    <span>Em desenvolvimento ativo</span>
                </div>
                </div>

            </div>
            </div>
        </div>

        </Template>
    </AuthenticatedPage>
  );
}

           