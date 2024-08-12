import React from "react";
import { useState } from "react";
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

const ResetPassword=()=>{

    //show password
    const [input, setInput] = useState({
        otpnum: '',
        password: '',
        cPassword: ''
      });
     
      const [error, setError] = useState({
        otpnum:'',
        password: '',
        cPassword: ''
      })
     
      const onInputChange = e => {

        const { name, value } = e.target;
        setInput(prev => ({
            ...prev,
            [name]: value
        }));
        validateInput(e);
     
      }
     
      const validateInput = e => {
        let { name, value } = e.target;
        setError(prev => {
          const stateObj = { ...prev, [name]: "" };
       
          switch (name) {
            case "optnum":
              if (!value) {
                stateObj[name] = "Please enter OTP";
              }
              break;
       
            case "password":
              if (!value) {
                stateObj[name] = "Please enter Password.";
              } else if (input.cPassword && value !== input.cPassword) {
                stateObj["cPassword"] = "Password and Confirm Password does not match.";
              } else {
                stateObj["cPassword"] = input.cPassword ? "" : error.cPassword;
              }
              break;
       
            case "cPassword":
              if (!value) {
                stateObj[name] = "Please enter Confirm Password.";
              } else if (input.password && value !== input.password) {
                stateObj[name] = "Password and Confirm Password does not match.";
              }
              break;
       
            default:
              break;
          }
       
          return stateObj;
        });
      }





      const submitOtp=()=>{

    //validation
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
                    <h3>Reset Password Here</h3>
                  </CardHeader>
    
                  <CardBody>
                    <Form onSubmit={submitOtp} >
                      {/* Email field */}
    
                      <FormGroup>
                        <Label for="otpnum">Enter Otp</Label>
                        <Input
                          type="text"
                          id="otpnum"
                          value={input.otpnum}
                          onChange={onInputChange}
                          onBlur={validateInput} />
                          {error.otpnum && <span className='err'>{error.otpnum}</span>}
                      </FormGroup>
    
                      {/* password field */}
    
                      <FormGroup>
                        <Label for="password">Password</Label>
                    
                        <Input
                          type="password"
                          id="password"
                          value={input.password}
                          onChange={onInputChange}
                          onBlur={validateInput}
                        />
                        {error.password && <span className='err'>{error.password}</span>}
                      </FormGroup>

                      <FormGroup>
                        <Label for="cpassword">Confirm password</Label>
                        
                        <Input
                          type="password"
                          id="cpassword"
                          value={input.cPassword}
                          onChange={onInputChange}
                          onBlur={validateInput}
                          />
                          {error.cPassword && <span className='err'>{error.cPassword}</span>}
                      </FormGroup>
    
                      <Container className="text-center">
                        <Button color="light" outline
                        >
                          Create New Password
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

export default ResetPassword