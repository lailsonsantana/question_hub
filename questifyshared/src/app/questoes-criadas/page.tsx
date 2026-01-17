'use client'

import { AuthenticatedPage } from "@/components/AuthenticatedPage";
import MainTitle from "@/components/MainTitle";
import TableQuestion from "@/components/questao/table/TableQuestion";
import { Template } from "@/components/Template";
import { Skeleton } from "@mui/material";
import { useEffect, useState } from "react";

export default function QuestoesCriadasPage(){

    const [hasMounted, setHasMounted] = useState(false);

    useEffect(() => {
        setHasMounted(true);
    }, []);
      
    if (!hasMounted) {
        return null;
    }
      
    return(
        <AuthenticatedPage>
            <Template>
                <div className="flex flex-col items-center">
                    <MainTitle titulo="Questões criadas por você"/>
                    <div className="p-16">
                        <TableQuestion/>
                    </div>
                </div>
            </Template>
        </AuthenticatedPage>
    );
}