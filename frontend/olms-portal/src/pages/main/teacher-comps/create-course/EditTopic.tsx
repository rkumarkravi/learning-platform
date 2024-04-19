import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";
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
import { useEffect } from "react";
import MediaViewer from "@/reusables/MediaViewer";
export function EditTopic({ buttonTitle = "Submit", contentData }) {
  const formSchema = z.object({
    title: z.string().min(2, "Title is required!"),
    description: z.string().min(10, "Description is required!"),
    format: z.string().min(1, "Please select format"),
    file: contentData.url!=='NA' ? z.any().refine((file: File[]) => file?.length !== 0, "File is required"):z.any(),
  });
  type formSchemaType = z.infer<typeof formSchema>;
  const form = useForm<formSchemaType>({
    resolver: zodResolver(formSchema),
    defaultValues: { format: "ppt" },
  });
  const acceptTypesByCat: any = {
    ppt: ".ppt,.pptx",
    pdf: "application/pdf",
    img: "image/*",
    video: "video/*",
  };

  useEffect(() => {
    console.log("asdjashkd");
    if (contentData && contentData) {
      for (const key in contentData) {
        form.setValue(key, contentData[key]);
      }
    }
  }, []);

  const onSubmiEditTopic: SubmitHandler<formSchemaType> = async (data: any) => {
    console.log(form.formState.isValid);
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
        `/course-mgmt/content/${contentData.courseId}/${contentData.contentId}`,
        data,
        { "Content-Type": "multipart/form-data;" }
      );
      if (response.rs === "S") {
        toast({
          description: response.rd,
        });
      }
    }
  };
  const formElementClass: string = "flex flex-col gap-5 items-start w-full";
  const formErrorMessageClass: string = "text-red-400";

  async function deleteAction() {
    const response: ApiResponse = await axiosService(
      "DELETE",
      `/course-mgmt/content/media/${contentData.contentId}`,
      {}
    );
    if (response.rs === "S") {
      toast({
        description: response.rd,
      });
      contentData.url="NA";
      contentData.format="";
    }
  }

  return (
    <Drawer>
      <DrawerTrigger>{buttonTitle}</DrawerTrigger>

      <DrawerContent>
        <Form
          onSubmit={form.handleSubmit(onSubmiEditTopic)}
          control={form.control}
        >
          <DrawerHeader>
            <DrawerTitle>{contentData.title}</DrawerTitle>
            <DrawerDescription>
              Please upload the file and other details
            </DrawerDescription>
          </DrawerHeader>
          <div className="flex flex-col justify-evenly gap-5 sm:grid sm:grid-cols-2 sm:flex-wrap sm:justify-start p-2">
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
              <Label>Format:</Label>
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
              <Label>Media:</Label>
             {contentData.url==='NA' ? <Input
               type="file"
                {...form.register("file")}
                accept={acceptTypesByCat[form.getValues("format")]}
              />:
              <MediaViewer
                contentId={contentData.contentId}
                type={contentData.format}
                showDeleteIcon={true}
                className="w-24 h-24"
                deleteAction={()=>{deleteAction()}}
              />
             }
              <p className={formErrorMessageClass}>
                {form.formState.errors.file?.message}
              </p>
            </div>
          </div>

          <DrawerFooter>
            <Button
              type="submit"
              className="w-full mr-2 sm:w-36"
              disabled={form.formState.isSubmitting}
            >
              Submit {form.formState.isSubmitting}
            </Button>
            <DrawerClose>
              <Button variant="outline" type="button" className="w-full sm:w-36">
                Cancel
              </Button>
            </DrawerClose>
          </DrawerFooter>
        </Form>
      </DrawerContent>
    </Drawer>
  );
}
