import { useEffect, useState } from "react";
import {
  Card,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import axiosService from "@/services/Axios";
import { ApiResponse } from "@/models/Response";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
    const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  useEffect(() => {
    axiosService("POST", "/users/init", {}).then((x: ApiResponse) => {
      if (x && x.rs === "S") {
        setCourses(x.payload);
      }
    });
  }, []);

  function updateCourse(cid: any) {
    console.log(cid,location.pathname);
    navigate("createCourse",{state:{cid:cid,mode:"UPDATE"} });
  }
  return (
    <div className="flex flex-wrap gap-8">
      {courses.map((x:any,index) => (
        <Card key={index} className="max-w-96 text-left" onClick={()=>{updateCourse(x?.cid)}}>
          <CardHeader>
            <CardTitle>{x.title}</CardTitle>
            <CardDescription>Author: {x.author}</CardDescription>
          </CardHeader>
          {/* <CardContent>
            <p>
              Card Content: Include course details here, such as syllabus or
              objectives.
            </p>
          </CardContent> */}
          <CardFooter className="flex gap-2">
            <p>Likes: {x.likes}</p>
            <p>Certificate Available: {x.certificationAvailable?"YES":"NO"}</p>
          </CardFooter>
        </Card>
      ))}
    </div>
  )
}
