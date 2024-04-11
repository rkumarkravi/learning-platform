import { Button } from "@/components/ui/button";
import { useToast } from "@/components/ui/use-toast";
import { useData } from "@/main";
import axiosService from "@/services/Axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Signin = () => {
  const { data, setData } = useData();
  const {toast} = useToast();
  const navigate = useNavigate();
  const initData={email:"",password:""};
  const [formData, setFormData] = useState(initData);

  const handleFormData = (e: {
    target: {
      name:string, value: React.SetStateAction<string> };
  }) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    axiosService("POST","/auth/login",formData)
    .then(x=>{
      if(x.rs==='S'){
        toast({
          title: "Successful",
          description: `Welcome ${x.payload.fullName}`
        });
        
        x.payload.auth="SIGN";
        setData({ ...data, userDetail: x.payload });
        navigate("/main",{state:x});
      }else{
        toast({
          title: "Auth",
          description: x.rd
        });
      }
    });
  };

  return (
    <div className="w-full p-6 bg-black-400 rounded-md">
      <h2 className="text-2xl font-semibold mb-6">Signin</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label
            htmlFor="email"
            className="block text-left text-sm font-medium"
          >
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            className="mt-1 p-2 border rounded-md w-full"
            value={formData.email}
            onChange={handleFormData}
            placeholder="Enter your email id.."
          />
        </div>
        <div className="mb-4">
          <label
            htmlFor="password"
            className="block text-left text-sm font-medium "
          >
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            className="mt-1 p-2 border rounded-md w-full"
            value={formData.password}
            onChange={handleFormData}
            placeholder="Enter your password.."
          />
        </div>
        <div className="flex gap-1 justify-end">
          <Button
            type="submit"
            className="bg-black text-white px-4 py-2 rounded-md hover:bg-green-700"
          >
            Signin
          </Button>
          <Button
            type="reset"
            variant="ghost"
            onClick={()=>setFormData(initData)}
          >
            Clear
          </Button>
        </div>
      </form>
    </div>
  );
};

export default Signin;
