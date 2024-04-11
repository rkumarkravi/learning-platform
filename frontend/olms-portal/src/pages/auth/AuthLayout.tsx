import React, { useEffect, useState } from "react";
import Signin from "./Signin";
import Signup from "./Signup";
import { Button } from "@/components/ui/button";
import axiosService from "@/services/Axios";
import { ApiResponse } from "@/models/Response";
import { useNavigate } from "react-router-dom";

const AuthLayout: React.FC = () => {
  const [isSignin, setIsSignin] = useState(true);
  const [currentCl, setCurrentCl] = useState(0);
  const navigate = useNavigate();

  function handleChange(): void {
    setIsSignin(!isSignin);
  }

  useEffect(()=>{
    const at = localStorage.getItem("at");
    if (at) {
      const auth = { "Authorization": `Bearer ${at}` };
      axiosService("POST", "/auth/validTkn",{},auth).then((x: ApiResponse) => {
        if (x.rs === "S") {
          navigate("/main");
          return;
        }
      });
    }
  },[])

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentCl(Math.floor(Math.random() * 4) + 1);
    }, 3000);

    // Clean up the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, [currentCl]);

  const catchylines: string[] = [
    "Unlock Your Learning Potential Today",
    "Empower Your Education Journey",
    "Discover, Learn, Succeed",
    "Knowledge Begins Here",
    "Transforming Education Together",
  ];

  return (
    <div className="bg-black min-h-screen max-w-screen-xl flex flex-col lg:flex-row lg:gap-5 justify-center items-center">
      <div className="text-white lg:w-1/2 h-10 p-6 font-medium lg:text-4xl lg:animate-bounce animate-pulse sm:mb-10">
        {catchylines[currentCl]}!
      </div>
      <div className="lg:h-32 lg:w-px flex items-center hidden lg:block">
        <div className="h-full border-r border-gray-400"></div>
      </div>
      <div className="w-full lg:w-1/3 flex flex-col gap-3 text-white">
        <div>{isSignin ? <Signin /> : <Signup />}</div>
        <div className="flex flex-row gap-1 items-center justify-center">
          <span>
            {isSignin ? `Don't have an account ?` : `Already have an account ?`}
          </span>
          <Button onClick={handleChange} variant="ghost">
            {isSignin ? `Sign Up` : `Sign In`}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;
