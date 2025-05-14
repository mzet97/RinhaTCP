FROM mcr.microsoft.com/dotnet/sdk:9.0-alpine AS build
WORKDIR /src
COPY DotNetTCP.Client.csproj .
RUN dotnet restore "./DotNetTCP.Client.csproj"
COPY . .
RUN dotnet publish "DotNetTCP.Client.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM mcr.microsoft.com/dotnet/runtime:9.0-alpine AS final
WORKDIR /app
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "DotNetTCP.Client.dll"]