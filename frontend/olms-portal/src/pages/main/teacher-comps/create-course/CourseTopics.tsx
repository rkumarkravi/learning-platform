import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { z } from "zod";

import { zodResolver } from "@hookform/resolvers/zod";
import { Form, SubmitHandler, useForm } from "react-hook-form";
import { ApiResponse } from "@/models/Response";
import { toast } from "@/components/ui/use-toast";
import axiosService from "@/services/Axios";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { Card } from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import { EditTopic } from "./EditTopic";

function CourseTopics({cid,mode}) {
  const keyValue = useSelector((state: any) => state.keyValue.data);
  const formSchema = z.object({
    title: z.string().min(2, "Title is required!"),
    description: z.string().min(10, "Description is required!"),
    format: z.string().min(1, "Please select format"),
    file: z.any().refine((files) => files?.length == 1, "File is required."),
  });
  type formSchemaType = z.infer<typeof formSchema>;
  const form = useForm<formSchemaType>({
    resolver: zodResolver(formSchema),
    defaultValues: { format: "ppt" },
  });

  // useEffect(()=>{getContents();},[]);

  const acceptTypesByCat: any = {
    ppt: ".ppt,.pptx",
    pdf: "application/pdf",
    img: "image/*",
    video: "video/*",
  };

  const onSubmit: SubmitHandler<formSchemaType> = async (data: any) => {
    if (form.formState.isValid) {
      const formDataToSend = new FormData();
      for (const key in data) {
        console.log(key, data[key]);
        formDataToSend.append(key, data[key]);
      }
      //   formDataToSend.append("file", data.file[0]);
      console.log(data, formDataToSend);
      const response: ApiResponse = await axiosService(
        "POST",
        `/course-mgmt/content/${keyValue?.courseMetaData?.cid}`,
        data,
        { "Content-Type": "multipart/form-data;" }
      );
      if (response.rs === "S") {
        toast({
          description: response.rd,
        });
        getContents();
      }
    }
  };

  const [contents, setContents] = useState([]);
  const getContents = async () => {
    const response: ApiResponse = await axiosService(
      "GET",
      `/courses/content/${keyValue?.courseMetaData?.cid}`,
      {}
    );
    if (response.rs === "S") {
      setContents(response.payload);
    }
  };

  const formElementClass: string = "flex flex-col gap-5 items-start w-full";
  const formErrorMessageClass: string = "text-red-400";

  useEffect(() => {
    if (contents && contents.length == 0 && keyValue?.courseMetaData) {
      getContents();
    }
  }, []);

  return (
    <div>
      <Form
        // onSubmit={({ data, event }) => {
        //   // console.log(data);
        //   onSubmit(data, event);
        // }}
        onSubmit={form.handleSubmit(onSubmit)}
        control={form.control}
      >
        <div className="flex flex-col justify-evenly gap-5 sm:grid sm:grid-cols-2 sm:flex-wrap sm:justify-start">
          <div className={formElementClass}>
            <Label>Title:</Label>
            <Textarea {...form.register("title")} className="h-full" />
            <p className={formErrorMessageClass}>
              {form.formState.errors.title?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Description:</Label>
            <Textarea {...form.register("description")} className="min-h" />
            <p className={formErrorMessageClass}>
              {form.formState.errors.description?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Audience:</Label>
            <Select
              defaultValue={form.getValues("format")}
              onValueChange={(x) => {
                form.setValue("format", x);
              }}
            >
              <SelectTrigger className="w-full">
                <SelectValue
                  placeholder={`Select a ${form.register("format").name}`}
                />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="pdf">PDF</SelectItem>
                <SelectItem value="ppt">PPT</SelectItem>
                <SelectItem value="video">VIDEO</SelectItem>
                <SelectItem value="img">IMAGE</SelectItem>
              </SelectContent>
            </Select>
            <p className={formErrorMessageClass}>
              {form.formState.errors.format?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Picture:</Label>
            <Input
              type="file"
              {...form.register("file")}
              accept={acceptTypesByCat[form.getValues("format")]}
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.file?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Button
              type="submit"
              className="w-36"
              disabled={form.formState.isSubmitting}
            >
              Submit {form.formState.isSubmitting}
            </Button>
          </div>
        </div>
      </Form>
      {(contents && contents.length>0) && <div>
        <Separator className=" mt-5 mb-5"/>
        <h1 className="text-left">Course Topics:</h1>
        
        <Card className="mt-2">
          <div className="flex flex-row justify-between w-full p-2">
            <p className="text-sm font-medium w-1/2 text-left">Title</p>
            <Separator orientation="vertical"/>
            <p className="text-sm font-medium w-full text-left">Description</p>
            <Separator orientation="vertical"/>
            <p className="text-sm font-medium w-1/2 text-left ml-2">Format</p>
            <Separator orientation="vertical"/>
            <p className="text-sm font-medium w-1/2 text-left">Options</p>
          </div>
        </Card>
        {contents.map((content, index) => (
          <Card key={index} className="mt-2">
            <div className="flex flex-row justify-between w-full p-2">
              <p className="text-sm font-medium w-1/2 text-left">{content?.title}</p>
              <Separator orientation="vertical"/>
              <p className="text-sm font-medium w-full text-left ml-2">{content?.description}</p>
              <Separator orientation="vertical"/>
              <p className="text-sm font-medium w-1/2 text-left">{content?.format}</p>
              <Separator orientation="vertical"/>
              <p className="text-sm font-medium w-1/2 text-left"><EditTopic buttonTitle="Update" contentData={content}/></p>
            </div>
          </Card>
        ))}
      </div>
}
    </div>
  );
}

export default CourseTopics;
