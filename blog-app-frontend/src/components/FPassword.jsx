import { useState } from "react";
import { toast } from "react-toastify";

import {
  Label,
  Card,
  CardBody,
  CardHeader,
  Col,
  Container,
  Form,
  FormGroup,
  Input,
  Row,
  Button,
} from "reactstrap";
import Base from "../components/Base";
import { sendMail } from "../services/post-service"
 

const FPassword = () => {

  const [user, setEmail] = useState({email: ''});

  const [message, setMessage] = useState("");

  const submitEmail=()=>{

    
    sendMail(user)
    .then(data=>{
        console.log(data)
        setEmail({email: ''})
        window.location.href="/resetPassword"
        toast.success(data, {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: true,
          closeOnClick: false,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "light",
        });

        
    }).catch(error=>{
        console.log(error)
        setEmail("");
        setMessage(true)
    })
}
  

  return (
    <Base>
      <Container>
        <Row className="mt-4">
          <Col
            sm={{
              size: 6,
              offset: 3,
            }}
          >
  
            <Card color="dark" inverse>
              <CardHeader>
                <h3>Forget Password !!</h3>
              </CardHeader>
              <CardBody>
                
                <Form >
                  {/* Email field */}
                  
                  <FormGroup>
                    <Label>Enter Email</Label>
            
                    {message
                      ? <p style={{color:"red",fontWeight:"bold",fontSize:"12px"}}>Email Id is not correct </p>
                      : ''}
                

                    <Input
                      type="text"
                      id="email"
                      value={user.email}
                      onChange={(event) => setEmail({email:event.target.value})}
                      
                    />
                  </FormGroup>

                  <Container className="text-center">
                  <Button 
                    color="light" 
                    onClick={submitEmail}
                    >
                      Reset Password
                    </Button>
                  </Container>
                </Form>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </Base>
  );
};

export default FPassword;
