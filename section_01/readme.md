

to run ollama server, execute the following command in cmd - "ollama run llama3.2:1b"
1. include the ollama dependency
2. start the server by using the command "ollama run llama3.2"
3. take reference of the app.prop file and the RestController
================================================================

-docker model version #----checks version , simply run on cmd
-docker model run ai/gemma3 #----runs the ai model "gemma3"

(in docker Dekstop - go to the model option and chose your model and download it.)
================================================================
-the working app.properties for dockermodel runner -
 spring.ai.openai.chat.options.model=ai/gemma3
 spring.ai.openai.api-key=dummy
 # If the below value is not working, try setting the base URL to http://localhost:12434/engines/v1
 spring.ai.openai.base-url=http://localhost:12434/engines
================================================================
Spring AI can do much more - 
Message Roles
Advisors
Prompt Templates
Chat History Management
RAG ( Retrievel Augmented Generation)
Function calling (Tools Integration)
Building MC clients and server
================================================================
Concept of Defaults - refer to the pre configured values/behaviours that are automatically applied to each request received by the ChatClient. 

defaultSystem- sets default system message
 chatClientBuilder.defaultUsers("you're an HR, don't response to queries beyond HR domain.");
defaultAdvisors- Registers advisors like logging/custom filters that apply to each interation
 chatClientBilder.defaultAdvisors(new SimpleLoggingAdvisor);

defaultTools - Registers tools/functions available to the LLM
 chatClientBuilder.defaultTools(myToolClassInstance);

defaultUsers - Registers default user message that would be included in every prompt message unless overridden
 chatClientBuilder.defaultUsers("How can you help me?");

defaultOptions - Allows you to configure default model configuration options for the model request ( temperature, maxTokens, etc.)
 chatClientBuilder.defaultOptions(ChatOptions.builder()
                                  temperature(0.3).maxTokens(300).build());

we can override each one of them( as per the requirement) inside the rest end point

==================PromptStuffing==========
PromptStuffing  is useful for situations where a 100-200 lines of prompts are fed to the LLM. 
They can't process data when given in pages i.e. not good for BigData. 

====================ADVISORS==============

In the context of LLMs and AI agents, an Advisor is a component that guides another LLM or agent without directly 
performing the task itself. Think of it as a senior engineer giving suggestions to a developer rather than writing the code. 
The advisor influences decisions, planning, or prompting.

Why do we need Advisors?
LL models are:
expensive
general-purpose
not personalized
unaware of application-specific policies

An advisor adds application intelligence without changing the model itself.

Best Practices: 1. keep them request scoped or stateles, 2. chain multiples advisors if needed
3. use advisors for cross-cutting concerns instead of the core logic. 
4. Avoid alerting the meaning of prompts unleass intentional
User
↓
Advisor
↓
GPT-5
↓
Answer
The advisor might say:
"This is a coding problem."
"Use step-by-step reasoning."
"Search the documentation first."

| Advisor                               | Agent                |
| ------------------------------------- | -------------------- |
| Gives guidance                        | Performs work        |
| Doesn't own execution                 | Owns execution       |
| Doesn't call tools directly (usually) | Uses tools           |
| Suggests strategy                     | Carries out strategy |
| Lightweight                           | Autonomous           |

SpringAI Request flow:

                  User
                    │
                    ▼
            ChatClient.prompt()
                    │
                    ▼
     +-------------------------------+
     | Advisor 1 (Logging)           |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 2 (Memory)            |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 3 (RAG)               |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 4 (Safety)            |
     +-------------------------------+
                    │
                    ▼
             OpenAI / Gemini / Claude
                    │
                    ▼
     +-------------------------------+
     | Advisor 4 (Post Process)      |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 3                     |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 2                     |
     +-------------------------------+
                    │
                    ▼
     +-------------------------------+
     | Advisor 1                     |
     +-------------------------------+
                    │
                    ▼
                 Response
  
         Supervisor           <--- supervisor-Coordinates multiple agents -{assign work, merge results,  retry failures, monitor progress}
           /    |    \
      Agent1 Agent2 Agent3

    Advisor  <---- Guides one or more agents but doesn't orchestrate their execution. (Think deeper, Use search, Don't expose secrets) It doesn't split work or manage workers.
      |
     Agent