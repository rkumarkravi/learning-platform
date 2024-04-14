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
import { Form, useForm } from "react-hook-form";
import { z } from "zod";
import { ApiResponse } from "@/models/Response";
import axiosService from "@/services/Axios";
import { toast } from "@/components/ui/use-toast";
function CourseMetaDataForm({ className = "" }) {
  const formSchema = z.object({
    title: z.string().min(10, "Title is required!"),
    description: z.string().min(10),
    author: z.string(),
    durationInHours: z.number().min(1), // Corrected to number type
    level: z.enum(["BEGINNER", "INTERMEDIATE", "ADVANCED"]),
    prerequisites: z.array(z.string().min(1)),
    learningObjectives: z.string().min(1),
    topics: z.array(z.string()).min(1),
    format: z.enum(["Online", "In-person"]),
    language: z.string().min(1),
    keywords: z.array(z.string()).min(1),
    audience: z.enum(["GENERAL", "STUDENT", "PROFESSIONAL"]),
    certificationAvailable: z.boolean(),
    version: z.string(),
  });

  // Create the form using useForm with zodResolver
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      title: "",
      description: "",
      author: "",
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

  const onSubmit = async (values: z.infer<typeof formSchema>, event: any) => {
    event.preventDefault();
    console.error("form.formState.errors", form.formState.errors);
    console.log(values);
    if (form.formState.isValid) {
      const response: ApiResponse = await axiosService(
        "POST",
        "/course-mgmt/create",
        values
      );
      if (response.rs === "S") {
        toast({
          description: response.rd,
        });
      }
    }
  };

  const formElementClass: string = "flex flex-col gap-5 items-start w-96";
  const formErrorMessageClass: string = "text-red-400";
  function splitData(v: any) {
    return !v ? v.split(",") : [];
  }
  return (
    <div className={className}>
      <Form
        onSubmit={({ data, event }) => {
          // console.log(data);
          onSubmit(data, event);
        }}
        control={form.control}
      >
        <div className="flex flex-col justify-evenly gap-5 sm:flex-row sm:flex-wrap sm:justify-start">
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
            <Select {...form.register("level")}>
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
            <Select {...form.register("format")}>
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
                <SelectItem value="GENERAL">English</SelectItem>
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
            <Select {...form.register("audience")}>
              <SelectTrigger className="w-full">
                <SelectValue
                  placeholder={`Select a ${form.register("audience").name}`}
                />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="GENERAL">GENERAL</SelectItem>
                <SelectItem value="STUDENT">STUDENT</SelectItem>
                <SelectItem value="PROFESSIONAL">PROFESSIONAL</SelectItem>
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
            <Button type="submit" className="w-36">
              Submit
            </Button>
          </div>
        </div>
      </Form>
    </div>
  );
}

export default CourseMetaDataForm;
