import { Button } from "@/components/ui/button";
import React, { useState } from "react";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import * as Checkbox from "@radix-ui/react-checkbox";
import { CheckIcon } from "@radix-ui/react-icons";

function Signup() {
  // const [email, setEmail] = useState("");
  // const [password, setPassword] = useState("");
  const intData = {
    username: "",
    email: "",
    password: "",
    fullName: "",
    interests: [],
    role: "",
    phone:""
  };
  const [formData, setFormData] = useState(intData);
  const interestsOptions: string[] = ["React", "JavaScript", "HTML", "CSS"];

  const handleFormDataChange = (e: {
    target: {
      name: string;
      value: React.SetStateAction<string>;
    };
  }) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleCheckboxChange = (
    checked: Checkbox.CheckedState,
    value: string
  ) => {
    if (checked) {
      // Add the interest to the interests array
      setFormData((prevFormData) => ({
        ...prevFormData,
        interests: [...prevFormData.interests, value],
      }));
    } else {
      // Remove the interest from the interests array
      setFormData((prevFormData) => ({
        ...prevFormData,
        interests: prevFormData.interests.filter(
          (interest) => interest !== value
        ),
      }));
    }
  };

  const handleSubmit = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    console.log(Object.values(formData))
    if(!Object.values(formData).some(x=>x==='')){
      return;
    }
    console.log(formData);
  };

  return (
    <div className="w-full p-6 shadow-md rounded-md">
      <h2 className="text-2xl font-semibold mb-6">Register</h2>
      <div>
        <form onSubmit={handleSubmit}>
          <Accordion type="single" collapsible className="w-full" defaultValue="item-1">
            <AccordionItem value="item-1">
              <AccordionTrigger>Basic Info</AccordionTrigger>
              <AccordionContent>
                <div className="mb-4">
                  <label
                    htmlFor="username"
                    className="block text-left text-sm font-medium"
                  >
                    Username
                  </label>
                  <input
                    type="text"
                    id="username"
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.username}
                    name="username"
                    onChange={handleFormDataChange}
                    placeholder="Enter unique username.."
                  />
                </div>
                <div className="mb-4">
                  <label
                    htmlFor="fullname"
                    className="block text-left text-sm font-medium"
                  >
                    Full Name
                  </label>
                  <input
                    type="text"
                    id="fullName"
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.fullName}
                    name="fullName"
                    onChange={handleFormDataChange}
                    placeholder="Enter your full name.."
                  />
                </div>
                <div>
                  <label className="block text-left text-sm font-medium">
                    Interests
                  </label>
                  <div className="flex justify-evenly">
                    {interestsOptions.map((interest, index) => (
                      <div
                        style={{ display: "flex", alignItems: "center" }}
                        key={index}
                      >
                        <Checkbox.Root
                          className="mr-2 flex appearance-none items-center justify-center rounded-[4px] bg-black outline-none focus:shadow-[0_0_0_2px_black]"
                          id={interest}
                          value={interest}
                          onCheckedChange={(e: Checkbox.CheckedState) =>
                            handleCheckboxChange(e, interest)
                          }
                        >
                          <Checkbox.Indicator className="CheckboxIndicator text-violet11">
                            <CheckIcon />
                          </Checkbox.Indicator>
                        </Checkbox.Root>
                        <label className="Label rounded-sm p-1" htmlFor={interest}>
                          {interest}
                        </label>
                      </div>
                    ))}
                  </div>
                </div>
                <div className="mb-4">
                  <label
                    htmlFor="role"
                    className="block text-left text-sm font-medium"
                  >
                    Role
                  </label>
                  <input
                    type="text"
                    id="role"
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.role}
                    name="role"
                    onChange={handleFormDataChange}
                    placeholder="Select your role.."
                  />
                </div>
              </AccordionContent>
            </AccordionItem>
            <AccordionItem value="item-2">
              <AccordionTrigger>Communication Details</AccordionTrigger>
              <AccordionContent>
              <div className="mb-4">
                  <label
                    htmlFor="phone"
                    className="block text-left text-sm font-medium"
                  >
                    Phone
                  </label>
                  <input
                    type="tel"
                    id="phone"
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.phone}
                    name="phone"
                    onChange={handleFormDataChange}
                    placeholder="Enter your phone no.."
                  />
                </div>
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
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.email}
                    name="email"
                    onChange={handleFormDataChange}
                    placeholder="Enter your email id.."
                  />
                </div>
                <div className="mb-4">
                  <label
                    htmlFor="password"
                    className="block text-left text-sm font-medium"
                  >
                    Password
                  </label>
                  <input
                    type="password"
                    id="password"
                    className="mt-1 p-2 border rounded-md w-full"
                    value={formData.password}
                    name="password"
                    onChange={handleFormDataChange}
                    placeholder="Enter your strong password.."
                  />
                </div>
              </AccordionContent>
            </AccordionItem>
            <div className="flex gap-1 justify-end mt-4">
              <Button
                type="submit"
                className="bg-black text-white px-4 py-2 rounded-md hover:bg-green-700"
              >
                Signup
              </Button>
              <Button
                type="reset"
                variant="ghost"
                onClick={() => setFormData(intData)}
              >
                Clear
              </Button>
            </div>
          </Accordion>
        </form>
      </div>
    </div>
  );
}

export default Signup;
