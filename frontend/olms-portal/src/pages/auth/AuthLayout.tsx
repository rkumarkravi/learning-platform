import React, { useEffect, useState } from "react";
import Signin from "./Signin";
import Signup from "./Signup";
import { Button } from "@/components/ui/button";

const AuthLayout: React.FC = () => {
  const [isSignin,setIsSignin] = useState(false);
  const [currentCl,setCurrentCl] =useState(0);

  function handleChange(): void {
    setIsSignin(!isSignin);
  }

  useEffect(() => {
    const intervalId = setInterval(()=>{
        setCurrentCl(Math.floor(Math.random() * 4) + 1);
      },3000);

    // Clean up the interval when the component unmounts
    return () => clearInterval(intervalId);
  }, [currentCl]);

  const catchylines:string[]=["","Unlock Your Learning Potential Today","Empower Your Education Journey",
  "Discover, Learn, Succeed",
  "Knowledge Begins Here",
  "Transforming Education Together"];

  return (
    <div className="min-h-screen max-w-screen-xl flex flex-row gap-5 justify-center items-center">
      <div className="w-1/2 p-6 font-medium text-4xl animate-bounce">
        {catchylines[currentCl]} !
      </div>
      <div className="h-32 flex items-center">
        <div className="h-full border-r border-gray-400"></div>
      </div>
      <div className="w-1/3 flex flex-col gap-3">
        <div>{isSignin ? <Signin /> : <Signup />}</div>
        <div className="flex flex-row gap-1 items-center justify-center">
          <span>{isSignin ? `Dont have an account ?` : `Already have an account ?`}</span>
          <Button onClick={handleChange} variant="ghost">
            {isSignin ? `SignUp` : `SignIn`}
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;
