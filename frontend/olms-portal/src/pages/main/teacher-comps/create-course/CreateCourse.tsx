import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import CourseMetaDataForm from "./CourseMetaDataForm";
import CourseTopics from "./CourseTopics";
import { Card, CardTitle } from "@/components/ui/card";
import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";

function CreateCourse() {
  const { state } = useLocation();
  const keyValue = useSelector((state: any) => state.keyValue.data);
  console.log(state.cid,location.pathname);
  return (
    <div className="sm:flex">
      <Tabs defaultValue="course-metadata-form" className="sm:flex sm:flex-col gap-2 sm:w-full">
        <TabsList className=" w-min h-min sm:flex sm:w-1/4">
          <TabsTrigger value="course-metadata-form" className="sm:w-full">
            Course Details
          </TabsTrigger>
          <TabsTrigger value="course-topics" className="sm:w-full">
            Course Topics
          </TabsTrigger>
        </TabsList>
        <div className="flex-1">
          <Card className="sm:w-full pl-4 py-5 text-left mb-5 mt-2">
            <CardTitle>Course Name : {keyValue?.courseMetaData?.title}</CardTitle>
          </Card>
          <TabsContent value="course-metadata-form">
            <CourseMetaDataForm cid={state?.cid} mode={state?.mode} />
          </TabsContent>
          <TabsContent value="course-topics">
            <CourseTopics cid={state?.cid} mode={state?.mode} />
          </TabsContent>
        </div>
      </Tabs>
    </div>
  );
}

export default CreateCourse;
