import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { Textarea } from "@/components/ui/textarea";
import { zodResolver } from "@hookform/resolvers/zod";
import { Form, SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import { ApiResponse } from "@/models/Response";
import axiosService from "@/services/Axios";
import { toast } from "@/components/ui/use-toast";
import { useDispatch, useSelector } from "react-redux";
import { setValue } from "@/store/newway/key-value-slice";
import { useEffect } from "react";
function CourseMetaDataForm({ className = "",cid,mode="CREATE"}) {

  const keyValue = useSelector((state: any) => state.keyValue.data);
  const dispatch = useDispatch();

  const callForUpdate=async()=>{
    if(mode==='UPDATE' && cid){
      const response: ApiResponse = await axiosService(
        "POST",
        `/courses/${cid}`,
        {}
      );
      if (response.rs === "S") {
        dispatch(setValue({key:"courseMetaData",value:response.payload}));
      }
    }
  }

  useEffect(()=>{callForUpdate();},[]);

  useEffect(()=>{
    
    if(keyValue && keyValue.courseMetaData){
        for (const key in keyValue.courseMetaData) {
          form.setValue(key, keyValue.courseMetaData[key]);
        }
    }
  },[keyValue])

  const formSchema = z.object({
    title: z.string().min(10, "Title is required!"),
    description: z.string().min(10, "Description is required!"),
    // author: z.string(),
    durationInHours: z.number().min(1), // Corrected to number type
    level: z.string(),
    prerequisites: z.array(z.string().min(1)),
    learningObjectives: z.string().min(1),
    topics: z.array(z.string()).min(1),
    format: z.string(),
    language: z.string().min(1),
    keywords: z.array(z.string()).min(1),
    audience: z.string(),
    certificationAvailable: z.boolean(),
    version: z.string(),
  });

  type formSchemaType = z.infer<typeof formSchema>;
  // Create the form using useForm with zodResolver
  const form = useForm<formSchemaType>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      title: "",
      description: "",
      // author: "",
      durationInHours: 0, // Provide a default value
      level: "BEGINNER",
      prerequisites: [],
      learningObjectives: "",
      topics: [],
      format: "Online",
      language: "English",
      keywords: [],
      audience: "GENERAL",
      certificationAvailable: false,
      version: "1.0",
    },
  });

  const onSubmit: SubmitHandler<formSchemaType> = async (
    data: formSchemaType
  ) => {
    // event.preventDefault();
    console.error("form.formState.errors",form.formState.dirtyFields, form.formState.isValid,form.formState.isDirty);
    console.log(data, form.formState.isSubmitting);
    if (form.formState.isValid) {
      // setTimeout(() => {}, 10000); 
      const response: ApiResponse = await axiosService(
        "POST",
        "/course-mgmt/create",
        data
      );
      if (response.rs === "S") {
        dispatch(setValue({key:"courseMetaData",value:response.payload}));
        toast({
          description: response.rd,
        });
      }
    }
  };

  const formElementClass: string = "flex flex-col gap-5 items-start w-full";
  const formErrorMessageClass: string = "text-red-400";
  function splitData(v: any) {
    console.log(v);
    if(typeof v ==='object'){
      return v.join(",");
    }
    return v && v.length > 0 ? v.split(",") : [];
  }
  return (
    <div className={className}>
      <Form
        // onSubmit={({ data, event }) => {
        //   // console.log(data);
        //   onSubmit(data, event);
        // }}
        onSubmit={form.handleSubmit(onSubmit)}
        control={form.control}
      >
        <div className="flex flex-col justify-evenly gap-5 sm:grid sm:grid-cols-2 sm:flex-row sm:flex-wrap sm:justify-start">
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
          {/* <div className={formElementClass}>
            <Label>Author:</Label>
            <Input type="text" {...form.register("author")} />
          </div> */}
          <div className={formElementClass}>
            <Label>Duration (in hours):</Label>
            <Input
              type="number"
              {...form.register("durationInHours", { valueAsNumber: true })}
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.durationInHours?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Level:</Label>
            <Select
              defaultValue={form.getValues("level")}
              onValueChange={(x) => {
                form.setValue("level", x);
              }}
            >
              <SelectTrigger className="w-full">
                <SelectValue
                  placeholder={`Select a ${form.register("level").name}`}
                />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="BEGINNER">BEGINNER</SelectItem>
                <SelectItem value="INTERMEDIATE">INTERMEDIATE</SelectItem>
                <SelectItem value="ADVANCED">ADVANCED</SelectItem>
              </SelectContent>
            </Select>
            <p className={formErrorMessageClass}>
              {form.formState.errors.level?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Prerequisites:</Label>
            <Textarea
              {...form.register("prerequisites", {
                setValueAs: (v) => splitData(v),
              })}
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.prerequisites?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Learning Objectives:</Label>
            <Textarea {...form.register("learningObjectives")} />
            <p className={formErrorMessageClass}>
              {form.formState.errors.learningObjectives?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Topics:</Label>
            <Textarea
              {...form.register("topics", { setValueAs: (v) => splitData(v) })}
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.topics?.message}
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
                <SelectItem value="Online">Online</SelectItem>
                <SelectItem value="In-person">In-person</SelectItem>
              </SelectContent>
            </Select>
            <p className={formErrorMessageClass}>
              {form.formState.errors.format?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Language:</Label>
            <Select {...form.register("language")}>
              <SelectTrigger className="w-full">
                <SelectValue
                  placeholder={`Select a ${form.register("language").name}`}
                />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="English">English</SelectItem>
              </SelectContent>
            </Select>
            <p className={formErrorMessageClass}>
              {form.formState.errors.language?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Keywords:</Label>
            <Textarea
              {...form.register("keywords", {
                setValueAs: (v) => splitData(v),
              })}
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.keywords?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Audience:</Label>
            <Select
              defaultValue={form.getValues("audience")}
              onValueChange={(x) => {
                form.setValue("audience", x);
              }}
            >
              <SelectTrigger className="w-full">
                <SelectValue
                  placeholder={`Select a ${form.register("audience").name}`}
                />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="GENERAL">GENERAL</SelectItem>
                <SelectItem value="STUDENTS">STUDENTS</SelectItem>
                <SelectItem value="PROFESSIONALS">PROFESSIONALS</SelectItem>
                <SelectItem value="EDUCATORS">EDUCATORS</SelectItem>
              </SelectContent>
            </Select>
            <p className={formErrorMessageClass}>
              {form.formState.errors.audience?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Certification Available:</Label>
            <Switch
              {...form.register("certificationAvailable")}
              defaultChecked={form.getValues("certificationAvailable")}
              onCheckedChange={(e) =>
                form.setValue("certificationAvailable", e)
              }
            />
            <p className={formErrorMessageClass}>
              {form.formState.errors.certificationAvailable?.message}
            </p>
          </div>
          <div className={formElementClass}>
            <Label>Version:</Label>
            <Input type="text" {...form.register("version")} />
            <p className={formErrorMessageClass}>
              {form.formState.errors.version?.message}
            </p>
          </div>
          {/* //add elements above this only */}
          <div className={formElementClass}></div>
          <div className={formElementClass}>
            <Button
              type="submit"
              className="w-36"
              disabled={form.formState.isSubmitting}
            >
              {mode?mode:"Submit"}
            </Button>
          </div>
        </div>
      </Form>
    </div>
  );
}

export default CourseMetaDataForm;
