import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import CourseMetaDataForm from "./CourseMetaDataForm";

function CreateCourse() {
  return (
    <div>
      <Tabs defaultValue="course-metadata-form" className="sm:flex gap-2">
        <TabsList className=" w-min h-min sm:flex sm:flex-col sm:w-1/4">
          <TabsTrigger value="course-metadata-form" className="sm:w-full">Course Details</TabsTrigger>
          <TabsTrigger value="course-topics" className="sm:w-full">Course Topics</TabsTrigger>
        </TabsList>
        <TabsContent value="course-metadata-form" className="flex-1">
          <CourseMetaDataForm/>
        </TabsContent>
        <TabsContent value="course-topics" className="flex-1">Change your password here.</TabsContent>
      </Tabs>
    </div>
  );
}

export default CreateCourse;
