import React, { useEffect, useState } from "react";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import axiosService from "@/services/Axios";
import { ApiResponse } from "@/models/Response";

function MainTeacher() {
  const [courses, setCourses] = useState([]);
  useEffect(() => {
    axiosService("POST", "/users/init", {}).then((x: ApiResponse) => {
      if (x && x.rs === "S") {
        setCourses(x.payload);
      }
    });
  }, []);

  return (
    <div className="flex flex-wrap gap-8">
      {courses.map((x:any,index) => (
        <Card key={index} className="max-w-96 text-left">
          <CardHeader>
            <CardTitle>{x.title}</CardTitle>
            <CardDescription>Author: {x.author}</CardDescription>
          </CardHeader>
          <CardContent>
            <p>
              Card Content: Include course details here, such as syllabus or
              objectives.
            </p>
          </CardContent>
          <CardFooter className="flex gap-2">
            <p>Likes: {x.likes}</p>
            <p>Certificate Available: {x.certificationAvailable?"YES":"NO"}</p>
          </CardFooter>
        </Card>
      ))}
    </div>
  );
}

export default MainTeacher;
